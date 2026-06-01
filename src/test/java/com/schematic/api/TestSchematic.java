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
import com.schematic.api.resources.features.types.CheckFlagsResponse;
import com.schematic.api.types.CheckFlagRequestBody;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.CheckFlagsResponseData;
import com.schematic.api.types.CreateEventRequestBody;
import com.schematic.api.types.EventBody;
import com.schematic.api.types.EventBodyIdentify;
import com.schematic.api.types.EventBodyIdentifyCompany;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.EventType;
import com.schematic.api.types.RulesengineCheckFlagResult;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    // --- Track/identify option mapping (buildTrackEvent / buildIdentifyEvent) ---

    @Test
    void buildTrackEvent_appliesAllOptions() {
        OffsetDateTime sentAt = OffsetDateTime.parse("2026-01-01T00:00:00Z");
        EventBody body = EventBody.of(EventBodyTrack.builder().event("e").build());

        CreateEventRequestBody event = Schematic.buildTrackEvent(
                body,
                TrackOptions.builder()
                        .idempotencyKey("idem-1")
                        .sentAt(sentAt)
                        .trustedClientClock(true)
                        .backfill(false)
                        .build());

        assertEquals(EventType.TRACK, event.getEventType());
        assertEquals("idem-1", event.getIdempotencyKey().get());
        assertEquals(sentAt, event.getSentAt().get());
        assertTrue(event.getTrustedClientClock().get());
        assertFalse(event.getBackfill().get());
    }

    @Test
    void buildTrackEvent_nullOptionsLeavesMetadataUnsetAndStampsSentAt() {
        EventBody body = EventBody.of(EventBodyTrack.builder().event("e").build());

        CreateEventRequestBody event = Schematic.buildTrackEvent(body, null);

        assertFalse(event.getIdempotencyKey().isPresent());
        assertFalse(event.getTrustedClientClock().isPresent());
        assertFalse(event.getBackfill().isPresent());
        assertTrue(event.getSentAt().isPresent(), "sent_at should default to now()");
    }

    @Test
    void buildTrackEvent_defaultsSentAtToNowWhenOptionOmitsIt() {
        EventBody body = EventBody.of(EventBodyTrack.builder().event("e").build());
        OffsetDateTime before = OffsetDateTime.now().minusSeconds(1);

        CreateEventRequestBody event = Schematic.buildTrackEvent(
                body, TrackOptions.builder().idempotencyKey("idem-1").build());

        OffsetDateTime after = OffsetDateTime.now().plusSeconds(1);
        assertTrue(event.getSentAt().isPresent());
        OffsetDateTime sentAt = event.getSentAt().get();
        assertTrue(sentAt.isAfter(before) && sentAt.isBefore(after));
        assertEquals("idem-1", event.getIdempotencyKey().get());
    }

    @Test
    void buildIdentifyEvent_appliesIdempotencyKey() {
        EventBody body = EventBody.of(EventBodyIdentify.builder()
                .keys(Collections.singletonMap("user_id", "u1"))
                .build());

        CreateEventRequestBody event = Schematic.buildIdentifyEvent(
                body, IdentifyOptions.builder().idempotencyKey("idem-2").build());

        assertEquals(EventType.IDENTIFY, event.getEventType());
        assertEquals("idem-2", event.getIdempotencyKey().get());
        assertTrue(event.getSentAt().isPresent());
    }

    @Test
    void buildIdentifyEvent_nullOptionsLeavesIdempotencyUnset() {
        EventBody body = EventBody.of(EventBodyIdentify.builder()
                .keys(Collections.singletonMap("user_id", "u1"))
                .build());

        CreateEventRequestBody event = Schematic.buildIdentifyEvent(body, null);

        assertFalse(event.getIdempotencyKey().isPresent());
        assertTrue(event.getSentAt().isPresent());
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

    // =========================================================================
    // checkFlags batch tests — mirrors schematic-ruby/test/custom.test.rb:953
    // =========================================================================

    @Test
    void checkFlags_OfflineModeReturnsDefaults() {
        Map<String, Boolean> defaults = new HashMap<>();
        defaults.put("flag-a", true);
        defaults.put("flag-b", false);
        Schematic offlineSchematic = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .flagDefaults(defaults)
                .logger(logger)
                .build();

        List<RulesengineCheckFlagResult> results =
                offlineSchematic.checkFlags(Arrays.asList("flag-a", "flag-b", "flag-c"), null, null);

        assertEquals(3, results.size());
        assertEquals("flag-a", results.get(0).getFlagKey());
        assertTrue(results.get(0).getValue());
        assertEquals("flag-b", results.get(1).getFlagKey());
        assertFalse(results.get(1).getValue());
        // unknown flag defaults to false
        assertEquals("flag-c", results.get(2).getFlagKey());
        assertFalse(results.get(2).getValue());
        for (RulesengineCheckFlagResult r : results) {
            assertEquals("Offline mode - using default value", r.getReason());
        }
    }

    @Test
    void checkFlags_OfflineModeNoKeysReturnsAllConfiguredDefaults() {
        Map<String, Boolean> defaults = new HashMap<>();
        defaults.put("default-a", true);
        defaults.put("default-b", false);
        Schematic offlineSchematic = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .flagDefaults(defaults)
                .logger(logger)
                .build();

        List<RulesengineCheckFlagResult> results = offlineSchematic.checkFlags(null, null, null);

        assertEquals(2, results.size());
        Map<String, Boolean> flagMap = new HashMap<>();
        for (RulesengineCheckFlagResult r : results) {
            flagMap.put(r.getFlagKey(), r.getValue());
        }
        assertTrue(flagMap.get("default-a"));
        assertFalse(flagMap.get("default-b"));
    }

    @Test
    void checkFlags_OfflineModeNoKeysNoDefaultsReturnsEmpty() {
        Schematic offlineSchematic = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .logger(logger)
                .build();

        List<RulesengineCheckFlagResult> results = offlineSchematic.checkFlags(null, null, null);

        assertTrue(results.isEmpty());
    }

    @Test
    void checkFlags_ReturnsValuesFromBulkApi() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(Schematic.builder()
                .apiKey("test_api_key")
                .cacheProviders(Collections.emptyList())
                .flagDefaults(Collections.singletonMap("flag-c", true))
                .logger(logger)
                .build());
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagsResponse response = CheckFlagsResponse.builder()
                .data(CheckFlagsResponseData.builder()
                        .flags(Arrays.asList(
                                CheckFlagResponseData.builder()
                                        .flag("flag-a")
                                        .reason("match")
                                        .value(true)
                                        .build(),
                                CheckFlagResponseData.builder()
                                        .flag("flag-b")
                                        .reason("no match")
                                        .value(false)
                                        .build()))
                        .build())
                .build();
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class))).thenReturn(response);

        List<RulesengineCheckFlagResult> results = spySchematic.checkFlags(
                Arrays.asList("flag-a", "flag-b", "flag-c"), Collections.singletonMap("org_id", "abc"), null);

        assertEquals(3, results.size());
        assertEquals("flag-a", results.get(0).getFlagKey());
        assertTrue(results.get(0).getValue());
        assertEquals("flag-b", results.get(1).getFlagKey());
        assertFalse(results.get(1).getValue());
        // flag-c not returned by API → falls back to default (true)
        assertEquals("flag-c", results.get(2).getFlagKey());
        assertTrue(results.get(2).getValue());
        assertEquals("Flag not found - using default value", results.get(2).getReason());
    }

    @Test
    void checkFlags_AllCachedReturnsWithoutBulkApiCall() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        Map<String, String> company = Collections.singletonMap("org_id", "abc");

        // Pre-populate cache for both flags via individual checks
        CheckFlagResponse responseX = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("flag-x")
                        .reason("match")
                        .value(true)
                        .build())
                .build();
        CheckFlagResponse responseY = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("flag-y")
                        .reason("no match")
                        .value(false)
                        .build())
                .build();
        when(featuresClient.checkFlag(eq("flag-x"), any(CheckFlagRequestBody.class)))
                .thenReturn(responseX);
        when(featuresClient.checkFlag(eq("flag-y"), any(CheckFlagRequestBody.class)))
                .thenReturn(responseY);

        spySchematic.checkFlag("flag-x", company, null);
        spySchematic.checkFlag("flag-y", company, null);

        // Batch check — both should come from cache, no bulk API call should fire
        List<RulesengineCheckFlagResult> results =
                spySchematic.checkFlags(Arrays.asList("flag-x", "flag-y"), company, null);

        assertEquals(2, results.size());
        assertTrue(results.get(0).getValue());
        assertFalse(results.get(1).getValue());
        verify(featuresClient, never()).checkFlags(any(CheckFlagRequestBody.class));
    }

    @Test
    void checkFlags_FetchesFreshValuesForAllKeysOnAnyCacheMiss() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        Map<String, String> company = Collections.singletonMap("org_id", "abc");

        // Pre-populate cache for one flag via individual check (stale value: true)
        CheckFlagResponse cachedResponse = CheckFlagResponse.builder()
                .data(CheckFlagResponseData.builder()
                        .flag("cached-flag")
                        .reason("old cached reason")
                        .value(true)
                        .build())
                .build();
        when(featuresClient.checkFlag(eq("cached-flag"), any(CheckFlagRequestBody.class)))
                .thenReturn(cachedResponse);
        spySchematic.checkFlag("cached-flag", company, null);

        // Bulk API returns updated value (false) for cached-flag, plus uncached-flag
        CheckFlagsResponse bulkResponse = CheckFlagsResponse.builder()
                .data(CheckFlagsResponseData.builder()
                        .flags(Arrays.asList(
                                CheckFlagResponseData.builder()
                                        .flag("cached-flag")
                                        .reason("updated reason")
                                        .value(false)
                                        .build(),
                                CheckFlagResponseData.builder()
                                        .flag("uncached-flag")
                                        .reason("new match")
                                        .value(true)
                                        .build()))
                        .build())
                .build();
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class))).thenReturn(bulkResponse);

        List<RulesengineCheckFlagResult> results =
                spySchematic.checkFlags(Arrays.asList("cached-flag", "uncached-flag"), company, null);

        assertEquals(2, results.size());
        // cached-flag should use fresh API value (false), not stale cached value (true)
        assertFalse(results.get(0).getValue(), "should use fresh API value, not stale cache");
        assertEquals("updated reason", results.get(0).getReason());
        assertTrue(results.get(1).getValue());
    }

    @Test
    void checkFlags_ReturnsDefaultsOnApiError() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(Schematic.builder()
                .apiKey("test_api_key")
                .cacheProviders(Collections.emptyList())
                .flagDefaults(Collections.singletonMap("err-flag", true))
                .logger(logger)
                .build());
        when(spySchematic.features()).thenReturn(featuresClient);
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        List<RulesengineCheckFlagResult> results =
                spySchematic.checkFlags(Arrays.asList("err-flag", "other-flag"), null, null);

        assertEquals(2, results.size());
        // err-flag has a configured default of true
        assertTrue(results.get(0).getValue());
        // other-flag has no default → false
        assertFalse(results.get(1).getValue());
        verify(logger).error(contains("Error checking flags via API"));
    }

    @Test
    void checkFlags_NoKeysCallsBulkApi() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(Schematic.builder()
                .apiKey("test_api_key")
                .cacheProviders(Collections.emptyList())
                .logger(logger)
                .build());
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagsResponse response = CheckFlagsResponse.builder()
                .data(CheckFlagsResponseData.builder()
                        .flags(Arrays.asList(
                                CheckFlagResponseData.builder()
                                        .flag("auto-a")
                                        .reason("match")
                                        .value(true)
                                        .build(),
                                CheckFlagResponseData.builder()
                                        .flag("auto-b")
                                        .reason("no match")
                                        .value(false)
                                        .build()))
                        .build())
                .build();
        when(featuresClient.checkFlags(any(CheckFlagRequestBody.class))).thenReturn(response);

        List<RulesengineCheckFlagResult> results =
                spySchematic.checkFlags(null, Collections.singletonMap("org_id", "abc"), null);

        assertEquals(2, results.size());
        assertEquals("auto-a", results.get(0).getFlagKey());
        assertTrue(results.get(0).getValue());
        assertEquals("auto-b", results.get(1).getFlagKey());
        assertFalse(results.get(1).getValue());
    }
}
