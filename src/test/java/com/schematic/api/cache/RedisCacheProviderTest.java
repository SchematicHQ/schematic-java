package com.schematic.api.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

@ExtendWith(MockitoExtension.class)
class RedisCacheProviderTest {

    @Mock
    private JedisPooled jedis;

    private Map<String, String> store;

    @BeforeEach
    void setUp() {
        store = new HashMap<>();
    }

    @Test
    void deleteMissing_withMultiSegmentPrefix_doesNotTouchSiblingCaches() {
        // Regression: a user-supplied keyPrefix containing an internal colon used to cause
        // deleteMissing to derive a scan pattern that matched every cache under the same
        // namespace, silently wiping company/user caches whenever a flag snapshot synced.
        String keyPrefix = "myapp:schematic:";
        store.put("myapp:schematic:flags:v1:flag-a", "{}"); // keep
        store.put("myapp:schematic:flags:v1:flag-b", "{}"); // keep
        store.put("myapp:schematic:flags:v1:flag-stale", "{}"); // must be deleted
        store.put("myapp:schematic:company:v1:comp-1", "{}"); // sibling — must NOT be deleted
        store.put("myapp:schematic:user:v1:user-1", "{}"); // sibling — must NOT be deleted
        wirePatternAwareScan();

        RedisCacheProvider<String> provider =
                new RedisCacheProvider<>(jedis, Duration.ofSeconds(60), keyPrefix, String.class);

        provider.deleteMissing(Arrays.asList("flags:v1:flag-a", "flags:v1:flag-b"), "flags:*");

        verify(jedis).del(new String[] {"myapp:schematic:flags:v1:flag-stale"});
        verify(jedis, times(1)).del(any(String[].class));
    }

    @Test
    void deleteMissing_withDefaultPrefix_stillIsolatesByScanPattern() {
        String keyPrefix = "schematic:";
        store.put("schematic:flags:v1:flag-a", "{}");
        store.put("schematic:flags:v1:flag-stale", "{}");
        store.put("schematic:company:v1:comp-1", "{}");
        wirePatternAwareScan();

        RedisCacheProvider<String> provider =
                new RedisCacheProvider<>(jedis, Duration.ofSeconds(60), keyPrefix, String.class);

        provider.deleteMissing(Arrays.asList("flags:v1:flag-a"), "flags:*");

        verify(jedis).del(new String[] {"schematic:flags:v1:flag-stale"});
    }

    @Test
    void deleteMissing_withNullScanPattern_isNoOp() {
        // Without an explicit scan scope we'd have to wildcard the entire keyPrefix,
        // which would match sibling caches. deleteMissing must refuse to guess.
        RedisCacheProvider<String> provider =
                new RedisCacheProvider<>(jedis, Duration.ofSeconds(60), "schematic:", String.class);

        provider.deleteMissing(Arrays.asList("flags:v1:flag-a"), null);
        provider.deleteMissing(Arrays.asList("flags:v1:flag-a"), "");

        verify(jedis, never()).scan(anyString(), any(ScanParams.class));
        verify(jedis, never()).del(any(String[].class));
    }

    @Test
    void deleteMissing_withoutScanPattern_isNoOp() {
        // The one-arg deleteMissing inherits CacheProvider's default (no-op) because
        // Redis has no safe way to scope a wipe without a pattern.
        RedisCacheProvider<String> provider =
                new RedisCacheProvider<>(jedis, Duration.ofSeconds(60), "schematic:", String.class);

        provider.deleteMissing(Arrays.asList("flags:v1:flag-a"));

        verify(jedis, never()).scan(anyString(), any(ScanParams.class));
        verify(jedis, never()).del(any(String[].class));
    }

    @Test
    void deleteMissing_withEmptyKeysList_isNoOp() {
        RedisCacheProvider<String> provider =
                new RedisCacheProvider<>(jedis, Duration.ofSeconds(60), "schematic:", String.class);

        provider.deleteMissing(null, "flags:*");
        provider.deleteMissing(java.util.Collections.emptyList(), "flags:*");

        verify(jedis, never()).scan(anyString(), any(ScanParams.class));
        verify(jedis, never()).del(any(String[].class));
    }

    /**
     * Wires up {@code jedis.scan} to filter our fake {@link #store} by the actual MATCH
     * pattern on the {@link ScanParams} passed in — so a too-broad scan pattern would
     * return sibling-cache keys, exactly as real Redis would.
     */
    private void wirePatternAwareScan() {
        when(jedis.scan(anyString(), any(ScanParams.class))).thenAnswer(invocation -> {
            ScanParams params = invocation.getArgument(1);
            String glob = extractMatchPattern(params);
            Pattern regex = globToRegex(glob);
            List<String> matches = store.keySet().stream()
                    .filter(k -> regex.matcher(k).matches())
                    .collect(Collectors.toList());
            return new ScanResult<>(ScanParams.SCAN_POINTER_START, matches);
        });
    }

    // ScanParams doesn't expose a public getter for the MATCH pattern, so read it via
    // reflection. In Jedis 5.2.0 the internal field is an EnumMap<Keyword, ByteBuffer>.
    // Update if the internal field layout changes.
    @SuppressWarnings("unchecked")
    private static String extractMatchPattern(ScanParams params) throws Exception {
        Field paramsField = ScanParams.class.getDeclaredField("params");
        paramsField.setAccessible(true);
        Map<?, ByteBuffer> paramsMap = (Map<?, ByteBuffer>) paramsField.get(params);
        for (Map.Entry<?, ByteBuffer> entry : paramsMap.entrySet()) {
            if (entry.getKey().toString().equalsIgnoreCase("MATCH")) {
                return new String(entry.getValue().array());
            }
        }
        throw new AssertionError("ScanParams has no MATCH pattern set");
    }

    private static Pattern globToRegex(String glob) {
        StringBuilder sb = new StringBuilder();
        for (char c : glob.toCharArray()) {
            if (c == '*') sb.append(".*");
            else if ("\\.^$|?+()[]{}".indexOf(c) >= 0) sb.append('\\').append(c);
            else sb.append(c);
        }
        return Pattern.compile(sb.toString());
    }
}
