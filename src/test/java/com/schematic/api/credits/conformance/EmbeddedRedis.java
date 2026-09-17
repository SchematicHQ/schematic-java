package com.schematic.api.credits.conformance;

import java.io.IOException;
import java.net.ServerSocket;
import redis.clients.jedis.JedisPooled;
import redis.embedded.RedisServer;

/**
 * One embedded Redis for the whole test JVM.
 *
 * <p>A real server rather than a fake: the stores' Lua is byte-identical to the other SDKs', and
 * only a real Redis runs it, TIME and all.
 */
final class EmbeddedRedis {

    private static RedisServer server;
    private static JedisPooled client;

    static synchronized JedisPooled client() {
        if (client != null) {
            return client;
        }
        try {
            int port = freePort();
            server = RedisServer.newRedisServer().port(port).build();
            server.start();
            client = new JedisPooled("localhost", port);
            Runtime.getRuntime().addShutdownHook(new Thread(EmbeddedRedis::stop));
            return client;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to start the embedded Redis the conformance suite runs on", e);
        }
    }

    private static synchronized void stop() {
        if (client != null) {
            client.close();
            client = null;
        }
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                // Shutting down anyway.
            }
            server = null;
        }
    }

    private static int freePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private EmbeddedRedis() {}
}
