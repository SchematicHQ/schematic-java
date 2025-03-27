/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Response;

public final class BaseSchematicHttpResponse<T> {

    private final T body;

    private final Map<String, List<String>> headers;

    public BaseSchematicHttpResponse(T body, Response rawResponse) {
        this.body = body;

        Map<String, List<String>> headers = new HashMap<>();
        rawResponse.headers().forEach(header -> {
            String key = header.component1();
            String value = header.component2();
            headers.computeIfAbsent(key, _str -> new ArrayList<>()).add(value);
        });
        this.headers = headers;
    }

    public T body() {
        return this.body;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }
}
