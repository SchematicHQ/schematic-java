/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.resources.plangroups;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.schematic.api.core.BaseSchematicApiException;
import com.schematic.api.core.BaseSchematicException;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.MediaTypes;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.core.RequestOptions;
import com.schematic.api.errors.BadRequestError;
import com.schematic.api.errors.ForbiddenError;
import com.schematic.api.errors.InternalServerError;
import com.schematic.api.errors.NotFoundError;
import com.schematic.api.errors.UnauthorizedError;
import com.schematic.api.resources.plangroups.requests.CreatePlanGroupRequestBody;
import com.schematic.api.resources.plangroups.requests.UpdatePlanGroupRequestBody;
import com.schematic.api.resources.plangroups.types.CreatePlanGroupResponse;
import com.schematic.api.resources.plangroups.types.GetPlanGroupResponse;
import com.schematic.api.resources.plangroups.types.UpdatePlanGroupResponse;
import com.schematic.api.types.ApiError;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PlangroupsClient {
    protected final ClientOptions clientOptions;

    public PlangroupsClient(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
    }

    public GetPlanGroupResponse getPlanGroup() {
        return getPlanGroup(null);
    }

    public GetPlanGroupResponse getPlanGroup(RequestOptions requestOptions) {
        HttpUrl httpUrl = HttpUrl.parse(this.clientOptions.environment().getUrl())
                .newBuilder()
                .addPathSegments("plan-groups")
                .build();
        Request okhttpRequest = new Request.Builder()
                .url(httpUrl)
                .method("GET", null)
                .headers(Headers.of(clientOptions.headers(requestOptions)))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        OkHttpClient client = clientOptions.httpClient();
        if (requestOptions != null && requestOptions.getTimeout().isPresent()) {
            client = clientOptions.httpClientWithTimeout(requestOptions);
        }
        try (Response response = client.newCall(okhttpRequest).execute()) {
            ResponseBody responseBody = response.body();
            if (response.isSuccessful()) {
                return ObjectMappers.JSON_MAPPER.readValue(responseBody.string(), GetPlanGroupResponse.class);
            }
            String responseBodyString = responseBody != null ? responseBody.string() : "{}";
            try {
                switch (response.code()) {
                    case 401:
                        throw new UnauthorizedError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 403:
                        throw new ForbiddenError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 404:
                        throw new NotFoundError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 500:
                        throw new InternalServerError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                }
            } catch (JsonProcessingException ignored) {
                // unable to map error response, throwing generic error
            }
            throw new BaseSchematicApiException(
                    "Error with status code " + response.code(),
                    response.code(),
                    ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class));
        } catch (IOException e) {
            throw new BaseSchematicException("Network error executing HTTP request", e);
        }
    }

    public CreatePlanGroupResponse createPlanGroup(CreatePlanGroupRequestBody request) {
        return createPlanGroup(request, null);
    }

    public CreatePlanGroupResponse createPlanGroup(CreatePlanGroupRequestBody request, RequestOptions requestOptions) {
        HttpUrl httpUrl = HttpUrl.parse(this.clientOptions.environment().getUrl())
                .newBuilder()
                .addPathSegments("plan-groups")
                .build();
        RequestBody body;
        try {
            body = RequestBody.create(
                    ObjectMappers.JSON_MAPPER.writeValueAsBytes(request), MediaTypes.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            throw new BaseSchematicException("Failed to serialize request", e);
        }
        Request okhttpRequest = new Request.Builder()
                .url(httpUrl)
                .method("POST", body)
                .headers(Headers.of(clientOptions.headers(requestOptions)))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        OkHttpClient client = clientOptions.httpClient();
        if (requestOptions != null && requestOptions.getTimeout().isPresent()) {
            client = clientOptions.httpClientWithTimeout(requestOptions);
        }
        try (Response response = client.newCall(okhttpRequest).execute()) {
            ResponseBody responseBody = response.body();
            if (response.isSuccessful()) {
                return ObjectMappers.JSON_MAPPER.readValue(responseBody.string(), CreatePlanGroupResponse.class);
            }
            String responseBodyString = responseBody != null ? responseBody.string() : "{}";
            try {
                switch (response.code()) {
                    case 400:
                        throw new BadRequestError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 401:
                        throw new UnauthorizedError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 403:
                        throw new ForbiddenError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 500:
                        throw new InternalServerError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                }
            } catch (JsonProcessingException ignored) {
                // unable to map error response, throwing generic error
            }
            throw new BaseSchematicApiException(
                    "Error with status code " + response.code(),
                    response.code(),
                    ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class));
        } catch (IOException e) {
            throw new BaseSchematicException("Network error executing HTTP request", e);
        }
    }

    public UpdatePlanGroupResponse updatePlanGroup(String planGroupId, UpdatePlanGroupRequestBody request) {
        return updatePlanGroup(planGroupId, request, null);
    }

    public UpdatePlanGroupResponse updatePlanGroup(
            String planGroupId, UpdatePlanGroupRequestBody request, RequestOptions requestOptions) {
        HttpUrl httpUrl = HttpUrl.parse(this.clientOptions.environment().getUrl())
                .newBuilder()
                .addPathSegments("plan-groups")
                .addPathSegment(planGroupId)
                .build();
        RequestBody body;
        try {
            body = RequestBody.create(
                    ObjectMappers.JSON_MAPPER.writeValueAsBytes(request), MediaTypes.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            throw new BaseSchematicException("Failed to serialize request", e);
        }
        Request okhttpRequest = new Request.Builder()
                .url(httpUrl)
                .method("PUT", body)
                .headers(Headers.of(clientOptions.headers(requestOptions)))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        OkHttpClient client = clientOptions.httpClient();
        if (requestOptions != null && requestOptions.getTimeout().isPresent()) {
            client = clientOptions.httpClientWithTimeout(requestOptions);
        }
        try (Response response = client.newCall(okhttpRequest).execute()) {
            ResponseBody responseBody = response.body();
            if (response.isSuccessful()) {
                return ObjectMappers.JSON_MAPPER.readValue(responseBody.string(), UpdatePlanGroupResponse.class);
            }
            String responseBodyString = responseBody != null ? responseBody.string() : "{}";
            try {
                switch (response.code()) {
                    case 400:
                        throw new BadRequestError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 401:
                        throw new UnauthorizedError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 403:
                        throw new ForbiddenError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 404:
                        throw new NotFoundError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                    case 500:
                        throw new InternalServerError(
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, ApiError.class));
                }
            } catch (JsonProcessingException ignored) {
                // unable to map error response, throwing generic error
            }
            throw new BaseSchematicApiException(
                    "Error with status code " + response.code(),
                    response.code(),
                    ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class));
        } catch (IOException e) {
            throw new BaseSchematicException("Network error executing HTTP request", e);
        }
    }
}
