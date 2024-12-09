package com.schematic.api;

import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Environment;

public final class Schematic extends BaseSchematic {

    public Schematic(ClientOptions clientOptions) {
        super(clientOptions);
    }

    // TODO: add custom methods here

    public static Schematic.Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ClientOptions.Builder clientOptionsBuilder = ClientOptions.builder();

        private String apiKey = null;

        private Environment environment = Environment.DEFAULT;

        /**
         * Sets apiKey
         */
        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder environment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public Builder url(String url) {
            this.environment = Environment.custom(url);
            return this;
        }

        public BaseSchematic build() {
            if (apiKey == null) {
                throw new RuntimeException("Please provide apiKey");
            }
            this.clientOptionsBuilder.addHeader("X-Schematic-Api-Key", this.apiKey);
            clientOptionsBuilder.environment(this.environment);
            return new BaseSchematic(clientOptionsBuilder.build());
        }
    }

}
