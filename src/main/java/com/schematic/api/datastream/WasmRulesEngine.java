package com.schematic.api.datastream;

import com.dylibso.chicory.runtime.ExportFunction;
import com.dylibso.chicory.runtime.HostFunction;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.runtime.Memory;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;
import com.dylibso.chicory.wasi.WasiPreview1_ModuleFactory;
import com.dylibso.chicory.wasm.Parser;
import com.dylibso.chicory.wasm.WasmModule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.RulesengineCheckFlagResult;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineFlag;
import com.schematic.api.types.RulesengineUser;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * WASM-based rules engine for local flag evaluation.
 *
 * <p>Loads the Schematic rules engine WASM binary (compiled from Rust) and evaluates
 * flag rules locally using cached flag definitions, company data, and user data.
 * Uses the Chicory pure-Java WASM runtime (no native dependencies).
 *
 * <p>Data flow:
 * <ul>
 *   <li>Input: typed objects serialized to snake_case JSON via Jackson @JsonProperty annotations</li>
 *   <li>Output: camelCase JSON from WASM, converted to snake_case, deserialized into generated types</li>
 * </ul>
 */
public class WasmRulesEngine implements RulesEngine {

    private static final String DEFAULT_WASM_RESOURCE = "/wasm/rulesengine.wasm";

    private final SchematicLogger logger;
    private final String wasmResourcePath;

    private volatile boolean initialized;
    private volatile String cachedVersionKey;
    private Instance instance;
    private Memory memory;
    private ExportFunction allocFn;
    private ExportFunction deallocFn;
    private ExportFunction checkFlagFn;
    private ExportFunction setTimeFn;
    private ExportFunction getResultJsonFn;
    private ExportFunction getResultJsonLengthFn;

    public WasmRulesEngine(SchematicLogger logger) {
        this(logger, DEFAULT_WASM_RESOURCE);
    }

    public WasmRulesEngine(SchematicLogger logger, String wasmResourcePath) {
        this.logger = logger;
        this.wasmResourcePath = wasmResourcePath;
        this.initialized = false;
    }

    /**
     * Initializes the WASM rules engine by loading the binary and resolving exports.
     * Safe to call multiple times (idempotent).
     */
    public synchronized void initialize() {
        if (initialized) {
            return;
        }

        try {
            log("info", "Initializing WASM rules engine from " + wasmResourcePath);

            InputStream wasmStream = getClass().getResourceAsStream(wasmResourcePath);
            if (wasmStream == null) {
                throw new RuntimeException("WASM binary not found at resource path: " + wasmResourcePath);
            }

            WasmModule module = Parser.parse(wasmStream);

            // Auto-flush the WASM stderr/stdout so that eprintln! from the rules
            // engine surfaces immediately (without this, errors are buffered and
            // can appear to be silent).
            java.io.PrintStream wasmStderr = new java.io.PrintStream(System.err, true);
            java.io.PrintStream wasmStdout = new java.io.PrintStream(System.out, true);
            WasiOptions wasiOptions = WasiOptions.builder()
                    .withStdout(wasmStdout)
                    .withStderr(wasmStderr)
                    .build();
            WasiPreview1 wasi = WasiPreview1.builder().withOptions(wasiOptions).build();
            HostFunction[] hostFunctions = WasiPreview1_ModuleFactory.toHostFunctions(wasi);

            ImportValues importValues = ImportValues.builder()
                    .withFunctions(Arrays.asList(hostFunctions))
                    .build();

            instance = Instance.builder(module).withImportValues(importValues).build();

            memory = instance.memory();
            allocFn = instance.export("alloc");
            deallocFn = instance.export("dealloc");
            checkFlagFn = instance.export("checkFlagCombined");
            // Optional: feed the wasm the current wall-clock time before each
            // check. The raw wasm has no clock under Chicory (SCHY-471); without
            // it, metric-period reset timestamps are omitted. Absent on older
            // wasm builds, so resolve it defensively.
            try {
                setTimeFn = instance.export("setCurrentTimeMillis");
            } catch (Exception e) {
                setTimeFn = null;
            }
            getResultJsonFn = instance.export("getResultJson");
            getResultJsonLengthFn = instance.export("getResultJsonLength");

            initialized = true;
            log("info", "WASM rules engine initialized successfully");
        } catch (Exception e) {
            log("error", "Failed to initialize WASM rules engine: " + e.getMessage());
            throw new RuntimeException("Failed to initialize WASM rules engine", e);
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String getVersionKey() {
        if (!initialized) {
            return null;
        }
        String cached = cachedVersionKey;
        if (cached != null) {
            return cached;
        }
        synchronized (this) {
            if (cachedVersionKey != null) {
                return cachedVersionKey;
            }
            try {
                ExportFunction versionKeyFn = instance.export("get_version_key_wasm");
                long[] result = versionKeyFn.apply();
                int ptr = (int) result[0];
                cachedVersionKey = memory.readCString(ptr);
                return cachedVersionKey;
            } catch (Exception e) {
                log("warn", "Failed to get WASM version key: " + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public RulesengineCheckFlagResult checkFlag(RulesengineFlag flag, RulesengineCompany company, RulesengineUser user)
            throws Exception {
        if (!initialized) {
            throw new IllegalStateException("WASM rules engine not initialized");
        }

        // Build envelope using the SDK's ObjectMapper. @JsonProperty annotations
        // produce snake_case JSON; the WASM rules engine accepts snake_case via
        // #[serde(alias = "...")] on its Rust types.
        ObjectMapper mapper = ObjectMappers.JSON_MAPPER;
        ObjectNode envelope = mapper.createObjectNode();
        // `null` entity context for the flag subtree (flag rules can reference
        // either user or company traits, so we can't assume one); "company"
        // and "user" for their respective subtrees.
        envelope.set("flag", sanitize(mapper.valueToTree(flag), null));
        if (company != null) {
            envelope.set("company", sanitize(mapper.valueToTree(company), "company"));
        }
        if (user != null) {
            envelope.set("user", sanitize(mapper.valueToTree(user), "user"));
        }

        String inputJson = mapper.writeValueAsString(envelope);
        String resultJson = callWasm(inputJson);

        // WASM returns camelCase JSON; generated types expect snake_case.
        // Convert keys before deserializing.
        JsonNode camelNode = mapper.readTree(resultJson);
        JsonNode snakeNode = camelToSnakeKeys(camelNode);

        return mapper.treeToValue(snakeNode, RulesengineCheckFlagResult.class);
    }

    /**
     * Calls into the WASM runtime. This method is synchronized because the WASM instance
     * uses shared linear memory — concurrent calls would corrupt each other's data.
     * Under high concurrency, flag evaluations are serialized through this lock.
     */
    private synchronized String callWasm(String inputJson) {
        byte[] data = inputJson.getBytes(StandardCharsets.UTF_8);
        int length = data.length;

        long[] allocResult = allocFn.apply(length);
        int ptr = (int) allocResult[0];

        try {
            memory.write(ptr, data);

            // Supply the host's current time so the engine can compute
            // metric-period reset timestamps (SCHY-471). No-op on older wasm.
            if (setTimeFn != null) {
                setTimeFn.apply(System.currentTimeMillis());
            }

            long[] checkResult = checkFlagFn.apply(ptr, length);
            int resultLen = (int) checkResult[0];

            if (resultLen < 0) {
                throw new RuntimeException("WASM checkFlagCombined returned error code: " + resultLen);
            }

            long[] resultPtrArr = getResultJsonFn.apply();
            int resultPtr = (int) resultPtrArr[0];

            long[] resultLenArr = getResultJsonLengthFn.apply();
            int actualLen = (int) resultLenArr[0];

            byte[] resultBytes = memory.readBytes(resultPtr, actualLen);
            return new String(resultBytes, StandardCharsets.UTF_8);
        } finally {
            deallocFn.apply(ptr, length);
        }
    }

    /**
     * Recursively converts JSON object keys from camelCase to snake_case.
     */
    static JsonNode camelToSnakeKeys(JsonNode node) {
        if (node.isObject()) {
            ObjectMapper mapper = ObjectMappers.JSON_MAPPER;
            ObjectNode result = mapper.createObjectNode();
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String snakeKey = camelToSnake(field.getKey());
                result.set(snakeKey, camelToSnakeKeys(field.getValue()));
            }
            return result;
        } else if (node.isArray()) {
            ObjectMapper mapper = ObjectMappers.JSON_MAPPER;
            com.fasterxml.jackson.databind.node.ArrayNode result = mapper.createArrayNode();
            for (JsonNode element : node) {
                result.add(camelToSnakeKeys(element));
            }
            return result;
        }
        return node;
    }

    /**
     * Recursively walks the tree and fixes any `trait_definition.entity_type`
     * that's missing or empty. The WASM rules engine's `EntityType` enum only
     * accepts "user" or "company" — empty strings cause the entire parse to
     * fail with error code -1.
     *
     * <p>When {@code defaultEntityType} is non-null (i.e. the subtree is rooted
     * at a company or user object), we inject it so the condition still
     * evaluates correctly. When it's null (flag subtree), we can't determine
     * the entity type, so we drop the `trait_definition` (the condition field
     * is optional and the rule still evaluates).
     *
     * <p>Workaround for replicator data that writes `entity_type: ""`. Once
     * the replicator writes correct values, this is a no-op.
     */
    static JsonNode sanitize(JsonNode node, String defaultEntityType) {
        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;
            JsonNode td = obj.get("trait_definition");
            if (td != null && td.isObject()) {
                ObjectNode tdObj = (ObjectNode) td;
                JsonNode et = tdObj.get("entity_type");
                boolean missing = et == null || !et.isTextual() || et.asText().isEmpty();
                if (missing) {
                    if (defaultEntityType != null) {
                        tdObj.put("entity_type", defaultEntityType);
                    } else {
                        obj.remove("trait_definition");
                    }
                }
            }
            Iterator<Map.Entry<String, JsonNode>> fields = obj.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                sanitize(field.getValue(), defaultEntityType);
            }
        } else if (node.isArray()) {
            for (JsonNode element : node) {
                sanitize(element, defaultEntityType);
            }
        }
        return node;
    }

    /**
     * Converts a camelCase string to snake_case.
     */
    static String camelToSnake(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void log(String level, String message) {
        if (logger == null) {
            return;
        }
        switch (level) {
            case "info":
                logger.info(message);
                break;
            case "warn":
                logger.warn(message);
                break;
            case "error":
                logger.error(message);
                break;
            default:
                logger.debug(message);
                break;
        }
    }
}
