package com.schematic.contextsig;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ContextSignatureTest {

    // contextsig/context-signing.json is copied verbatim from
    // api/lib/contextsig/testdata in the Schematic API, which generates it from
    // the verifier and checks it in its own tests. Don't edit it here.
    private static JsonNode loadVectors() throws IOException {
        try (InputStream in = ContextSignatureTest.class.getResourceAsStream("/contextsig/context-signing.json")) {
            assertNotNull(in, "context-signing.json is missing");
            return new ObjectMapper().readTree(in);
        }
    }

    private static Map<String, String> keys(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        Map<String, String> keys = new LinkedHashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            keys.put(field.getKey(), field.getValue().asText());
        }
        return keys;
    }

    static Stream<Arguments> vectors() throws IOException {
        List<Arguments> args = new ArrayList<>();
        for (JsonNode v : loadVectors().get("vectors")) {
            args.add(Arguments.of(v.get("name").asText(), v));
        }
        return args.stream();
    }

    private static long[] window(String signature) {
        String[] parts = signature.split("\\.");
        assertEquals(3, parts.length);
        return new long[] {Long.parseLong(parts[0]), Long.parseLong(parts[1])};
    }

    private static Map<String, String> company() {
        return Collections.singletonMap("id", "comp_1");
    }

    @Test
    void capsTheLifetimeWhereTheApiDoes() throws IOException {
        assertEquals(loadVectors().get("max_lifetime_seconds").asLong(), ContextSignature.MAX_LIFETIME_SECONDS);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("vectors")
    void matchesSigningVector(String name, JsonNode v) {
        JsonNode context = v.get("context");
        Map<String, String> company = keys(context.get("company"));
        Map<String, String> user = keys(context.get("user"));

        assertEquals(v.get("canonical").asText(), ContextSignature.canonicalize(company, user));
        assertEquals(
                v.get("signature").asText(),
                ContextSignature.signAt(
                        v.get("secret").asText(),
                        company,
                        user,
                        v.get("iat").asLong(),
                        v.get("exp").asLong()));
    }

    @Test
    void signsForTheDefaultLifetimeFromNow() {
        long before = Instant.now().getEpochSecond();
        long[] window = window(ContextSignature.sign("secret", company(), null));

        assertTrue(window[0] >= before);
        assertTrue(window[0] <= Instant.now().getEpochSecond());
        assertEquals(ContextSignature.DEFAULT_LIFETIME_SECONDS, window[1] - window[0]);
    }

    @Test
    void signsFromTheTimeAndForTheLifetimeGiven() {
        Instant issuedAt = Instant.ofEpochMilli(1_700_000_000_500L);
        String signature = ContextSignature.sign(
                "secret",
                company(),
                null,
                ContextSignature.options().issuedAt(issuedAt).lifetimeSeconds(300));

        assertEquals(ContextSignature.signAt("secret", company(), null, 1_700_000_000L, 1_700_000_300L), signature);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -60, ContextSignature.MAX_LIFETIME_SECONDS + 1, 86_400})
    void refusesALifetimeTheApiWouldReject(long lifetimeSeconds) {
        assertThrows(
                ContextSignatureException.class,
                () -> ContextSignature.sign(
                        "secret", company(), null, ContextSignature.options().lifetimeSeconds(lifetimeSeconds)));
    }

    @Test
    void refusesAMissingSecret() {
        assertThrows(ContextSignatureException.class, () -> ContextSignature.sign("", company(), null));
        assertThrows(ContextSignatureException.class, () -> ContextSignature.sign(null, company(), null));
    }

    @Test
    void refusesANullValue() {
        Map<String, String> company = new HashMap<>();
        company.put("id", null);
        assertThrows(ContextSignatureException.class, () -> ContextSignature.sign("secret", company, null));
    }

    @Test
    void leavesOutANullCompanyAndUser() {
        assertEquals("{}", ContextSignature.canonicalize(null, null));
    }

    @Test
    void signsALoneSurrogateAsReplacementCharacterAsTheApiReadsIt() {
        assertEquals(
                "{\"company\":{\"name\":\"a�b\"}}",
                ContextSignature.canonicalize(Collections.singletonMap("name", "a\uD800b"), null));
        assertEquals(
                "{\"company\":{\"name\":\"a�\"}}",
                ContextSignature.canonicalize(Collections.singletonMap("name", "a\uDC00"), null));
    }

    @Test
    void keepsAValidSurrogatePair() {
        assertEquals(
                "{\"company\":{\"name\":\"😀\"}}",
                ContextSignature.canonicalize(Collections.singletonMap("name", "😀"), null));
    }
}
