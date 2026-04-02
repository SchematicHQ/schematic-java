package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.RulesengineCheckFlagResult;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchematicTest {
    @Mock
    private SchematicLogger logger;

    private Schematic schematic;
    private static final Duration DEFAULT_BUFFER_PERIOD = Duration.ofSeconds(3);

    @BeforeEach
    void setUp() {
        schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .eventBufferInterval(DEFAULT_BUFFER_PERIOD)
                .cacheProviders(Collections.singletonList(new LocalCache<RulesengineCheckFlagResult>()))
                .build();
    }

    @Test
    void checkFlag_HandlesNullData() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = mock(CheckFlagResponse.class);
        when(response.getData()).thenReturn(null);
        when(featuresClient.checkFlag(any(), any(CheckFlagRequestBody.class))).thenReturn(response);

        boolean result = spySchematic.checkFlag("test_flag", null, null);

        assertFalse(result);
        verify(logger).error(contains("Error checking flag via API"));
    }

    @Test
    void checkFlag_CachesResultIfNotCached() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("test_flag")
                        .reason("test_reason")
                        .value(true)
                        .build())
                .build();

        when(featuresClient.checkFlag(eq("test_flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(response);

        boolean result = spySchematic.checkFlag("test_flag", null, null);

        assertTrue(result);
        for (CacheProvider<RulesengineCheckFlagResult> provider : spySchematic.getFlagCheckCacheProviders()) {
            RulesengineCheckFlagResult cached = provider.get("test_flag");
            assertNotNull(cached);
            assertTrue(cached.getValue());
            assertEquals("test_reason", cached.getReason());
        }
    }

    @Test
    void checkFlag_ReturnsCachedValue() {
        String flagKey = "test_flag";
        RulesengineCheckFlagResult cachedResult = RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason("test_reason")
                .value(true)
                .build();
        for (CacheProvider<RulesengineCheckFlagResult> provider : schematic.getFlagCheckCacheProviders()) {
            provider.set(flagKey, cachedResult);
        }

        boolean result = schematic.checkFlag(flagKey, null, null);

        assertTrue(result);
    }

    @Test
    void checkFlag_UsesCorrectCacheKey() {
        String flagKey = "test_flag";
        String expectedCacheKey = "test_flag:c-name=test_company:u-id=unique_id";
        Map<String, String> company = Collections.singletonMap("name", "test_company");
        Map<String, String> user = Collections.singletonMap("id", "unique_id");

        RulesengineCheckFlagResult cachedResult = RulesengineCheckFlagResult.builder()
                .flagKey(flagKey)
                .reason("test_reason")
                .value(true)
                .build();
        for (CacheProvider<RulesengineCheckFlagResult> provider : schematic.getFlagCheckCacheProviders()) {
            provider.set(expectedCacheKey, cachedResult);
        }

        boolean result = schematic.checkFlag(flagKey, company, user);

        assertTrue(result);
    }

    @Test
    void checkFlag_ReturnsDefaultOnError() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(Schematic.builder()
                .apiKey("test_api_key")
                .flagDefaults(Collections.singletonMap("error_flag", true))
                .logger(logger)
                .build());
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlag(any(), any(CheckFlagRequestBody.class)))
                .thenThrow(new RuntimeException("API Error"));

        boolean result = spySchematic.checkFlag("error_flag", null, null);

        assertTrue(result);
        verify(logger).error(contains("Error checking flag via API"));
    }

    @Test
    void identify_EnqueuesEventNonBlocking() throws InterruptedException {
        Map<String, String> keys = Collections.singletonMap("user_id", "12345");
        EventBodyIdentifyCompany company =
                EventBodyIdentifyCompany.builder().name("test_company").build();
        Map<String, Object> traits = new HashMap<>();

        schematic.identify(keys, company, "John Doe", traits);
        Thread.sleep(100);

        verify(logger, never()).error(any());
    }

    @Test
    void track_EnqueuesEventNonBlocking() throws InterruptedException {
        Map<String, String> company = Collections.singletonMap("company_id", "67890");
        Map<String, String> user = Collections.singletonMap("user_id", "12345");
        Map<String, Object> traits = new HashMap<>();

        schematic.track("event_name", company, user, traits);
        Thread.sleep(100);

        verify(logger, never()).error(any());
    }

    @Test
    void track_OfflineMode() {
        Map<String, String> company = Collections.singletonMap("company_id", "67890");
        Map<String, String> user = Collections.singletonMap("user_id", "12345");
        Map<String, Object> traits = new HashMap<>();

        Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .logger(logger)
                .build()
                .track("test_event", company, user, traits);

        verify(logger, never()).error(any());
    }

    @Test
    void identify_OfflineMode() {
        Map<String, String> keys = Collections.singletonMap("user_id", "12345");
        EventBodyIdentifyCompany company =
                EventBodyIdentifyCompany.builder().name("test_company").build();

        Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .logger(logger)
                .build()
                .identify(keys, company, "John Doe", new HashMap<>());

        verify(logger, never()).error(any());
    }

    @Test
    void checkFlag_OfflineModeReturnsDefault() {
        boolean result = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .flagDefaults(Collections.singletonMap("test_flag", true))
                .logger(logger)
                .build()
                .checkFlag("test_flag", null, null);

        assertTrue(result);
    }

    @Test
    void checkFlag_OfflineModeNoDefault() {
        boolean result = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .logger(logger)
                .build()
                .checkFlag("unknown_flag", null, null);

        assertFalse(result);
    }

    @Test
    void checkFlag_ReturnsFalseOnErrorNoDefault() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic =
                spy(Schematic.builder().apiKey("test_api_key").logger(logger).build());
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlag(any(), any(CheckFlagRequestBody.class)))
                .thenThrow(new RuntimeException("API Error"));

        boolean result = spySchematic.checkFlag("no_default_flag", null, null);

        assertFalse(result);
        verify(logger).error(contains("Error checking flag via API"));
    }

    @Test
    void checkFlagWithEntitlement_Offline() {
        Schematic offlineSchematic = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .flagDefaults(Collections.singletonMap("offline_flag", true))
                .logger(logger)
                .build();

        RulesengineCheckFlagResult result = offlineSchematic.checkFlagWithEntitlement("offline_flag", null, null);

        assertTrue(result.getValue());
        assertEquals("flag default", result.getReason());
        assertEquals("offline_flag", result.getFlagKey());
    }

    @Test
    void checkFlagWithEntitlement_APIResponse() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("test_flag")
                        .reason("rule match")
                        .value(true)
                        .flagId("flag_123")
                        .companyId("company_456")
                        .userId("user_789")
                        .ruleId("rule_abc")
                        .build())
                .build();

        when(featuresClient.checkFlag(eq("test_flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(response);

        RulesengineCheckFlagResult result = spySchematic.checkFlagWithEntitlement("test_flag", null, null);

        assertTrue(result.getValue());
        assertEquals("rule match", result.getReason());
        assertEquals("test_flag", result.getFlagKey());
        assertEquals("flag_123", result.getFlagId().orElse(null));
        assertEquals("company_456", result.getCompanyId().orElse(null));
        assertEquals("user_789", result.getUserId().orElse(null));
        assertEquals("rule_abc", result.getRuleId().orElse(null));
    }

    @Test
    void checkFlagWithEntitlement_CacheHitPreservesAllFields() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("cached_flag")
                        .reason("rule match")
                        .value(true)
                        .flagId("flag_111")
                        .companyId("company_222")
                        .userId("user_333")
                        .ruleId("rule_444")
                        .build())
                .build();

        when(featuresClient.checkFlag(eq("cached_flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(response);

        // First call hits API and caches
        RulesengineCheckFlagResult firstResult = spySchematic.checkFlagWithEntitlement("cached_flag", null, null);

        // Second call should return cached result
        RulesengineCheckFlagResult secondResult = spySchematic.checkFlagWithEntitlement("cached_flag", null, null);

        // Verify API was only called once
        verify(featuresClient, times(1)).checkFlag(eq("cached_flag"), any(CheckFlagRequestBody.class));

        // Verify cached result preserves all metadata
        assertTrue(secondResult.getValue());
        assertEquals("rule match", secondResult.getReason());
        assertEquals("cached_flag", secondResult.getFlagKey());
        assertEquals("flag_111", secondResult.getFlagId().orElse(null));
        assertEquals("company_222", secondResult.getCompanyId().orElse(null));
        assertEquals("user_333", secondResult.getUserId().orElse(null));
        assertEquals("rule_444", secondResult.getRuleId().orElse(null));
    }

    @Test
    void checkFlag_ReturnAPIValueWhenCacheDisabled() {
        Schematic noCacheSchematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(logger)
                .cacheProviders(Collections.emptyList())
                .build();

        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(noCacheSchematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("no_cache_flag")
                        .reason("api reason")
                        .value(true)
                        .build())
                .build();

        when(featuresClient.checkFlag(eq("no_cache_flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(response);

        // Call twice - both should hit API since caching is disabled
        boolean result1 = spySchematic.checkFlag("no_cache_flag", null, null);
        boolean result2 = spySchematic.checkFlag("no_cache_flag", null, null);

        assertTrue(result1);
        assertTrue(result2);
        verify(featuresClient, times(2)).checkFlag(eq("no_cache_flag"), any(CheckFlagRequestBody.class));
    }

    @Test
    void checkFlag_DifferentCacheKeysForDifferentContexts() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse trueResponse = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("ctx_flag")
                        .reason("company A match")
                        .value(true)
                        .build())
                .build();

        CheckFlagResponse falseResponse = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("ctx_flag")
                        .reason("company B no match")
                        .value(false)
                        .build())
                .build();

        Map<String, String> companyA = Collections.singletonMap("id", "company_a");
        Map<String, String> companyB = Collections.singletonMap("id", "company_b");
        Map<String, String> userA = Collections.singletonMap("id", "user_a");
        Map<String, String> userB = Collections.singletonMap("id", "user_b");

        when(featuresClient.checkFlag(eq("ctx_flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(trueResponse)
                .thenReturn(falseResponse);

        boolean resultA = spySchematic.checkFlag("ctx_flag", companyA, userA);
        boolean resultB = spySchematic.checkFlag("ctx_flag", companyB, userB);

        assertTrue(resultA);
        assertFalse(resultB);
        // Both calls should hit API since they have different cache keys
        verify(featuresClient, times(2)).checkFlag(eq("ctx_flag"), any(CheckFlagRequestBody.class));
    }
}
