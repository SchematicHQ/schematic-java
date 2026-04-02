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
    private Instance instance;
    private Memory memory;
    private ExportFunction allocFn;
    private ExportFunction deallocFn;
    private ExportFunction checkFlagFn;
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

            WasiOptions wasiOptions = WasiOptions.builder().build();
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
    public RulesengineCheckFlagResult checkFlag(RulesengineFlag flag, RulesengineCompany company, RulesengineUser user)
            throws Exception {
        if (!initialized) {
            throw new IllegalStateException("WASM rules engine not initialized");
        }

        // Build envelope using the SDK's ObjectMapper — @JsonProperty annotations
        // produce snake_case JSON which the WASM engine accepts
        ObjectMapper mapper = ObjectMappers.JSON_MAPPER;
        ObjectNode envelope = mapper.createObjectNode();
        envelope.set("flag", mapper.valueToTree(flag));
        if (company != null) {
            envelope.set("company", mapper.valueToTree(company));
        }
        if (user != null) {
            envelope.set("user", mapper.valueToTree(user));
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
     * Returns the rules engine version key, used for cache invalidation.
     */
    public String getVersionKey() {
        if (!initialized) {
            return null;
        }
        try {
            ExportFunction versionKeyFn = instance.export("get_version_key_wasm");
            long[] result = versionKeyFn.apply();
            int ptr = (int) result[0];
            return memory.readCString(ptr);
        } catch (Exception e) {
            log("warn", "Failed to get WASM version key: " + e.getMessage());
            return null;
        }
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
