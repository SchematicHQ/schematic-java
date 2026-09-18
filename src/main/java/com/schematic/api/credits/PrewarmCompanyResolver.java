package com.schematic.api.credits;

import com.schematic.api.types.RulesengineCompany;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/**
 * Resolves the company id a prewarm acquires against, from keys that may carry no id.
 *
 * <p>Split out of the client so the waiting rule can be driven without a socket: a zero timeout
 * means cache-only rather than no resolution at all, which is the difference between a prewarm
 * that warms an already-cached company and one that never runs.
 */
public final class PrewarmCompanyResolver {

    private PrewarmCompanyResolver() {}

    /**
     * Returns the company id, or null when it does not surface in time.
     *
     * @param keys the caller's company keys
     * @param cached reads the company from the local cache only
     * @param fetch resolves the company over the wire, warming the cache as a side effect
     * @param timeout how long to keep fetching; zero or less is cache-only
     * @param pollInterval how long to wait between fetches
     * @param abort answers true when the caller has given up, for instance a closing client
     * @param onFetchError reports a failed fetch, which is retried until the timeout
     */
    public static String resolve(
            Map<String, String> keys,
            Function<Map<String, String>, RulesengineCompany> cached,
            Function<Map<String, String>, RulesengineCompany> fetch,
            Duration timeout,
            Duration pollInterval,
            BooleanSupplier abort,
            Function<RuntimeException, Void> onFetchError) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        String id = keys.get("id");
        if (id != null && !id.isEmpty()) {
            return id;
        }
        try {
            RulesengineCompany hit = cached.apply(keys);
            if (hit != null) {
                return hit.getId();
            }
        } catch (RuntimeException e) {
            // A cache that throws is a miss, not a failed prewarm: the fetch below answers the
            // same question over the wire.
            onFetchError.apply(e);
        }
        // A zero timeout is cache-only, not a refusal: the caller asked not to wait on the wire,
        // and the cache has already answered above.
        if (timeout == null || timeout.toMillis() <= 0) {
            return null;
        }

        // Retry across the brief connecting window at boot. A new company needs the preceding
        // identify ingested before the server can stream it back.
        long deadline = System.nanoTime() + timeout.toNanos();
        // The fetch runs on its own thread so the timeout bounds the fetch itself, not just the
        // gaps between attempts: a single call that never returns would otherwise hold the
        // prewarm past every deadline the caller set. Daemon, so a stuck one cannot keep the
        // process alive.
        ExecutorService fetcher = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "SchematicPrewarmResolve");
            thread.setDaemon(true);
            return thread;
        });
        try {
            while (true) {
                if (abort.getAsBoolean()) {
                    return null;
                }
                long remaining = deadline - System.nanoTime();
                if (remaining <= 0) {
                    return null;
                }
                Future<RulesengineCompany> pending = fetcher.submit(() -> fetch.apply(keys));
                try {
                    RulesengineCompany resolved = pending.get(remaining, TimeUnit.NANOSECONDS);
                    if (resolved != null) {
                        return resolved.getId();
                    }
                } catch (TimeoutException e) {
                    pending.cancel(true);
                    return null;
                } catch (ExecutionException e) {
                    onFetchError.apply(asRuntime(e.getCause()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                if (System.nanoTime() >= deadline) {
                    return null;
                }
                try {
                    Thread.sleep(pollInterval.toMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        } finally {
            fetcher.shutdownNow();
        }
    }

    private static RuntimeException asRuntime(Throwable cause) {
        return cause instanceof RuntimeException ? (RuntimeException) cause : new RuntimeException(cause);
    }
}
