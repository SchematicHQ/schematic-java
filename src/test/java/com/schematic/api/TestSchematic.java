package com.schematic.api;

import com.schematic.api.cache.CacheProvider;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.resources.features.types.CheckFlagResponse;
import com.schematic.api.types.CheckFlagResponseData;
import com.schematic.api.types.EventBodyIdentifyCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
                .cacheProviders(Collections.singletonList(new LocalCache<Boolean>()))
                .build();
    }

    @Test
    void checkFlag_HandlesNullData() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);
        
        CheckFlagResponse response = mock(CheckFlagResponse.class);
        when(response.getData()).thenReturn(null);
        when(featuresClient.checkFlag(any(), any())).thenReturn(response);
        
        boolean result = spySchematic.checkFlag("test_flag", null, null);
        
        assertFalse(result);
        verify(logger).error(contains("Error checking flag"));
    }

    @Test
    void checkFlag_CachesResultIfNotCached() {
        FeaturesClient featuresClient = mock(FeaturesClient.class);
        Schematic spySchematic = spy(schematic);
        when(spySchematic.features()).thenReturn(featuresClient);

        CheckFlagResponse response = CheckFlagResponse.builder()
            .data(CheckFlagResponseData.builder()
                .reason("test_reason")
                .value(true)
                .build())
            .build();
            
        when(featuresClient.checkFlag(eq("test_flag"), any())).thenReturn(response);
        
        boolean result = spySchematic.checkFlag("test_flag", null, null);

        assertTrue(result);
        for (CacheProvider<Boolean> provider : spySchematic.getFlagCheckCacheProviders()) {
            assertEquals(true, provider.get("test_flag"));
        }
    }

    @Test
    void checkFlag_ReturnsCachedValue() {
        String flagKey = "test_flag";
        for (CacheProvider<Boolean> provider : schematic.getFlagCheckCacheProviders()) {
            provider.set(flagKey, true);
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

        for (CacheProvider<Boolean> provider : schematic.getFlagCheckCacheProviders()) {
            provider.set(expectedCacheKey, true);
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
        when(featuresClient.checkFlag(any(), any())).thenThrow(new RuntimeException("API Error"));

        boolean result = spySchematic.checkFlag("error_flag", null, null);

        assertTrue(result);
        verify(logger).error(contains("Error checking flag"));
    }

    @Test
    void identify_EnqueuesEventNonBlocking() throws InterruptedException {
        Map<String, String> keys = Collections.singletonMap("user_id", "12345");
        EventBodyIdentifyCompany company = EventBodyIdentifyCompany.builder()
                .name("test_company")
                .build();
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
        EventBodyIdentifyCompany company = EventBodyIdentifyCompany.builder()
                .name("test_company")
                .build();
        
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
}