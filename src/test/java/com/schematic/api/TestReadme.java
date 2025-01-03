package com.schematic.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.cache.LocalCache;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.resources.companies.CompaniesClient;
import com.schematic.api.resources.companies.types.UpsertCompanyResponse;
import com.schematic.api.types.CompanyDetailResponseData;
import com.schematic.api.types.UpsertCompanyRequestBody;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Testing code examples used in README
@ExtendWith(MockitoExtension.class)
class SchematicReadmeTest {
    @Mock
    private SchematicLogger logger;

    @Test
    void testCompanyUpsert() {
        // Test the company upsert example from README
        CompaniesClient companiesClient = mock(CompaniesClient.class);
        Schematic spySchematic = spy(Schematic.builder().apiKey("test_api_key").build());
        when(spySchematic.companies()).thenReturn(companiesClient);

        Map<String, String> keys = new HashMap<>();
        keys.put("id", "your-company-id");

        Map<String, JsonNode> traits = new HashMap<>();
        traits.put("city", ObjectMappers.JSON_MAPPER.valueToTree("Atlanta"));
        traits.put("high_score", ObjectMappers.JSON_MAPPER.valueToTree(25));
        traits.put("is_active", ObjectMappers.JSON_MAPPER.valueToTree(true));

        UpsertCompanyRequestBody request = UpsertCompanyRequestBody.builder()
                .keys(keys)
                .name("Acme Widgets, Inc.")
                .traits(traits)
                .build();

        OffsetDateTime now = OffsetDateTime.now();
        CompanyDetailResponseData responseData = CompanyDetailResponseData.builder()
                .createdAt(now)
                .environmentId("test-env")
                .id("test-id")
                .name("Acme Widgets, Inc.")
                .updatedAt(now)
                .userCount(1)
                .build();

        UpsertCompanyResponse response =
                UpsertCompanyResponse.builder().data(responseData).build();

        when(companiesClient.upsertCompany(any())).thenReturn(response);

        UpsertCompanyResponse result = spySchematic.companies().upsertCompany(request);
        assertEquals("Acme Widgets, Inc.", result.getData().getName());
        verify(companiesClient).upsertCompany(request);
    }

    @Test
    void testClientBasicInitialization() {
        // Test basic client initialization from README
        Schematic schematic = Schematic.builder().apiKey("test_api_key").build();

        assertNotNull(schematic);
        assertEquals("test_api_key", schematic.getApiKey());
    }

    @Test
    void testClientWithLocalCache() {
        // Test client with local cache configuration
        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .cacheProviders(Collections.singletonList(new LocalCache<>()))
                .build();

        assertNotNull(schematic.getFlagCheckCacheProviders());
        assertEquals(1, schematic.getFlagCheckCacheProviders().size());
        assertTrue(schematic.getFlagCheckCacheProviders().get(0) instanceof LocalCache);
    }

    @Test
    void testClientWithDisabledCache() {
        // Test client with disabled caching
        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .cacheProviders(Collections.emptyList())
                .build();

        assertTrue(schematic.getFlagCheckCacheProviders().isEmpty());
    }

    @Test
    void testClientWithCustomEventBuffer() {
        // Test client with custom event buffer interval
        Duration customInterval = Duration.ofSeconds(5);
        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .eventBufferInterval(customInterval)
                .build();

        assertEquals(customInterval, schematic.getEventBufferInterval());
    }

    @Test
    void testClientWithOfflineModeAndDefaults() {
        // Test offline mode with flag defaults
        Map<String, Boolean> flagDefaults = new HashMap<>();
        flagDefaults.put("some-flag-key", true);

        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .offline(true)
                .flagDefaults(flagDefaults)
                .build();

        assertTrue(schematic.isOffline());
        assertTrue(schematic.checkFlag("some-flag-key", null, null));
    }
}
