package com.schematic.api.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.*;
import okio.Timeout;

/**
 * A no-operation OkHttpClient implementation that doesn't actually make HTTP requests.
 * This is used in offline mode to prevent any network calls.
 */
public class NoOpHttpClient extends OkHttpClient {

    private static final Response EMPTY_RESPONSE = new Response.Builder()
            .request(new Request.Builder().url("https://example.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
            .build();

    public NoOpHttpClient() {
        super(new Builder()
                .callTimeout(1, TimeUnit.MILLISECONDS)
                .connectTimeout(1, TimeUnit.MILLISECONDS)
                .readTimeout(1, TimeUnit.MILLISECONDS)
                .writeTimeout(1, TimeUnit.MILLISECONDS)
                .addInterceptor(chain -> EMPTY_RESPONSE));
    }

    @Override
    public Call newCall(Request request) {
        return new NoOpCall(request);
    }

    private static class NoOpCall implements Call {
        private final Request request;
        private boolean executed = false;
        private boolean canceled = false;

        public NoOpCall(Request request) {
            this.request = request;
        }

        @Override
        public Request request() {
            return request;
        }

        @Override
        public Response execute() throws IOException {
            if (executed) throw new IllegalStateException("Already Executed");
            if (canceled) throw new IOException("Canceled");
            executed = true;
            return EMPTY_RESPONSE;
        }

        @Override
        public void enqueue(Callback callback) {
            if (executed) {
                callback.onFailure(this, new IOException("Already Executed"));
                return;
            }
            if (canceled) {
                callback.onFailure(this, new IOException("Canceled"));
                return;
            }
            executed = true;
            try {
                callback.onResponse(this, EMPTY_RESPONSE);
            } catch (IOException e) {
                callback.onFailure(this, e);
            }
        }

        @Override
        public void cancel() {
            canceled = true;
        }

        @Override
        public boolean isExecuted() {
            return executed;
        }

        @Override
        public boolean isCanceled() {
            return canceled;
        }

        @Override
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @Override
        public Call clone() {
            return new NoOpCall(request);
        }
    }
}
