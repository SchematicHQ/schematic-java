package com.schematic.api.credits;

import com.schematic.api.logger.SchematicLogger;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Owns lease rows for one client: acquire on first use or after expiry, extend when the local
 * view dips below the water mark, release on close.
 *
 * <p>Acquire and extend each get their own best-effort single-flight map keyed by slot.
 * Best-effort because callers racing ahead of the registration can still issue duplicate wire
 * calls, which is safe: the server is idempotent for an active slot, {@link LeaseStore#replace}
 * keeps the first live lease, and {@link LeaseStore#extend} reconciles to a total.
 *
 * <p>Every path here resolves rather than throws: callers route a missing lease through their
 * fail-open or fail-closed handling, and several calls are fire-and-forget, where an exception
 * has nowhere to go.
 */
public final class CreditLeaseManager implements AutoCloseable {

    /**
     * How many in-flight extends one caller waits out before issuing its own. Two covers the case
     * the single-flight was written for: the flight a caller joins, and the follow-up another
     * caller registers while it was waiting.
     */
    private static final int MAX_EXTEND_JOINS = 2;

    private final LeaseWireClient wire;
    private final LeaseStore leases;
    private final ReservationStore reservations;
    private final CreditLeaseConfig config;
    private final SchematicLogger logger;
    private final Clock clock;
    private final Duration sweepInterval;

    // Kept separate so an in-flight extend can never satisfy an acquire, or the other way round.
    private final ConcurrentHashMap<String, Flight> acquireFlights = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Flight> extendFlights = new ConcurrentHashMap<>();
    // Lease work nobody waits on: the redundant release a lost acquire race issues, and the
    // background extends checks fire and forget. drain() waits these out so a close releases what
    // they installed.
    private final Set<CompletableFuture<?>> background = ConcurrentHashMap.newKeySet();

    private final ExecutorService executor;
    private final ScheduledExecutorService sweeper;
    private volatile boolean stopped;
    // Held across the flag write in stop() and the re-check an acquire makes once it owns the
    // slot's flight, which is what stops a lease landing in a slot close() has already swept.
    private final Object stopLock = new Object();
    // Compare-and-set rather than a read then a write: two threads starting the sweep together
    // would both pass a plain check and schedule a second sweeper onto the same store.
    private final AtomicBoolean sweeping = new AtomicBoolean();

    public CreditLeaseManager(
            LeaseWireClient wire,
            LeaseStore leases,
            ReservationStore reservations,
            CreditLeaseConfig config,
            SchematicLogger logger,
            Clock clock) {
        this.wire = wire;
        this.leases = leases;
        this.reservations = reservations;
        this.config = config != null ? config : CreditLeaseConfig.builder().build();
        this.logger = logger;
        this.clock = clock != null ? clock : Clock.systemUTC();
        this.sweepInterval = this.config.getSweepInterval() != null
                ? this.config.getSweepInterval()
                : CreditLeaseDefaults.SWEEP_INTERVAL;
        this.executor = Executors.newCachedThreadPool(daemonThreads("SchematicCreditLease"));
        this.sweeper = Executors.newSingleThreadScheduledExecutor(daemonThreads("SchematicCreditLeaseSweep"));
    }

    /** The lease knobs for one credit type. */
    public ResolvedLeaseConfig resolveConfig(String creditTypeId) {
        return config.resolve(creditTypeId);
    }

    /**
     * Returns the slot's live lease, acquiring one over the wire if none is live. Returns null
     * rather than throwing when the wire or the store is down, so the caller routes the outcome
     * through fail-open or fail-closed.
     */
    public LeaseState acquireIfNeeded(String companyId, String creditTypeId) {
        return acquireIfNeeded(companyId, creditTypeId, null);
    }

    /**
     * Acquires under the caller's per-check timeout. The flight is shared, so the first caller's
     * timeout governs everyone who joins it; a background caller passes null and takes the
     * client's own.
     */
    public LeaseState acquireIfNeeded(String companyId, String creditTypeId, Duration timeout) {
        if (stopped) {
            // A lease installed after releaseAllLocalLeases has listed the slots would be held
            // until it expires server-side, with nobody left to release it.
            debug("Not acquiring a credit lease for " + companyId + "/" + creditTypeId + ": the manager is stopped");
            return null;
        }
        LeaseState existing;
        try {
            existing = leases.get(companyId, creditTypeId);
        } catch (RuntimeException e) {
            error("Failed to read lease store for " + companyId + "/" + creditTypeId + ": " + e);
            return null;
        }
        // Judged on this process's clock; the Redis store re-checks against Redis's clock in
        // tryReserve, and the check handles a refusal there as a failure.
        if (existing != null && existing.isLiveAt(now())) {
            return existing;
        }
        // A stale row is left for replace to overwrite atomically; dropping it first could
        // clobber a lease a sibling just installed.
        if (stopped) {
            debug("Not acquiring a credit lease for " + companyId + "/" + creditTypeId + ": the manager is stopped");
            return null;
        }

        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        Flight joined = acquireFlights.get(key);
        if (joined != null) {
            return joined.await(timeout);
        }
        Flight flight = new Flight(0);
        Flight raced = acquireFlights.putIfAbsent(key, flight);
        if (raced != null) {
            return raced.await(timeout);
        }
        LeaseState result = null;
        try {
            boolean stoppedInTheGap;
            synchronized (stopLock) {
                // The check at the top can go stale by now; under stop()'s lock this one can't.
                stoppedInTheGap = stopped;
            }
            if (stoppedInTheGap) {
                debug("Not acquiring a credit lease for " + companyId + "/" + creditTypeId
                        + ": the manager stopped while the flight was being registered");
                return null;
            }
            result = acquire(companyId, creditTypeId, timeout);
            return result;
        } catch (RuntimeException e) {
            error("Failed to acquire credit lease for " + companyId + "/" + creditTypeId + ": " + e);
            return null;
        } finally {
            // Deregistered before completing so drain() doesn't spin on a finished flight, and
            // completed in finally so an Error can't leave joiners parked.
            acquireFlights.remove(key, flight);
            flight.result.complete(result);
        }
    }

    private LeaseState acquire(String companyId, String creditTypeId, Duration timeout) {
        ResolvedLeaseConfig resolved = resolveConfig(creditTypeId);
        LeaseGrant grant;
        try {
            grant = wire.acquire(
                    companyId, creditTypeId, resolved.getLeaseSize(), now().plus(resolved.getLeaseDuration()), timeout);
        } catch (RuntimeException e) {
            error("Failed to acquire credit lease for " + companyId + "/" + creditTypeId + ": " + e);
            return null;
        }
        boolean wrote;
        try {
            wrote = leases.replace(new LeaseGrant(
                    grant.getLeaseId(),
                    orElse(grant.getCompanyId(), companyId),
                    orElse(grant.getCreditTypeId(), creditTypeId),
                    grant.getGrantedAmount(),
                    grant.getExpiresAt()));
        } catch (RuntimeException e) {
            error("Failed to install credit lease " + grant.getLeaseId() + ": " + e);
            return null;
        }

        LeaseState current;
        try {
            current = leases.get(companyId, creditTypeId);
        } catch (RuntimeException e) {
            error("Failed to read lease store for " + companyId + "/" + creditTypeId + ": " + e);
            return null;
        }
        if (wrote) {
            debug("Acquired credit lease " + grant.getLeaseId() + " for " + companyId + "/" + creditTypeId
                    + " (granted=" + grant.getGrantedAmount() + ", expires=" + grant.getExpiresAt() + ")");
            return current;
        }

        // Lost the race to a sibling. The server usually hands a racing acquire the same lease,
        // which must not be released; only a different lease is redundant.
        if (current != null && !current.getLeaseId().equals(grant.getLeaseId())) {
            debug("Lost acquire race for " + companyId + "/" + creditTypeId + "; releasing redundant lease "
                    + grant.getLeaseId());
            // Allowed after stop, or the redundant lease holds its credits until expiry.
            spawn(
                    () -> {
                        try {
                            wire.release(grant.getLeaseId());
                        } catch (RuntimeException e) {
                            warn("Failed to release redundant credit lease " + grant.getLeaseId() + ": " + e);
                        }
                    },
                    true);
        } else {
            debug("Lost acquire race for " + companyId + "/" + creditTypeId + "; the server returned the installed "
                    + "lease " + grant.getLeaseId() + ", nothing to release");
        }
        return current;
    }

    /**
     * Extends the slot's lease when the local view warrants it, triggered by either the
     * low-water-mark ratio (steady-state refresh) or a {@code requiredCredits} hint above the
     * local remaining (a check just failed a reserve of that size). Pass null for
     * {@code requiredCredits} to ask for the steady-state check only.
     *
     * <p>A caller arriving while an extend is in flight joins it. If its own shortfall is larger
     * than what that extend asked for, it waits the flight out and then issues exactly one
     * follow-up extend for the remaining difference; otherwise it would inherit a tranche-sized
     * ask and fail its post-extend retry with credits still sitting on the server.
     */
    public LeaseState maybeExtend(String companyId, String creditTypeId, Double requiredCredits) {
        return maybeExtend(companyId, creditTypeId, requiredCredits, null);
    }

    /** Extends under the caller's per-check timeout, or the client's own when null. */
    public LeaseState maybeExtend(String companyId, String creditTypeId, Double requiredCredits, Duration timeout) {
        return maybeExtend(companyId, creditTypeId, requiredCredits, true, timeout);
    }

    private LeaseState maybeExtend(
            String companyId, String creditTypeId, Double requiredCredits, boolean joinInFlight, Duration timeout) {
        if (stopped) {
            debug("Not extending a credit lease for " + companyId + "/" + creditTypeId + ": the manager is stopped");
            return null;
        }
        String key = LeaseStore.leaseKey(companyId, creditTypeId);
        // One budget for every join and the extend this call may send.
        long startedAt = System.nanoTime();
        // Joins of flights too small for this caller are budgeted: once the budget is spent it
        // sends one extend of its own rather than queue behind an unbounded run of other callers'
        // follow-ups. A flight that covers its ask is always joined, since sending alongside it
        // would have the server grant both.
        for (int joinsLeft = MAX_EXTEND_JOINS; ; joinsLeft--) {
            LeaseState entry;
            try {
                entry = leases.get(companyId, creditTypeId);
            } catch (RuntimeException e) {
                warn("Failed to read lease store for " + companyId + "/" + creditTypeId + ": " + e);
                return null;
            }
            if (entry == null) {
                return null;
            }
            // The server has already refunded an expired lease; the next check acquires afresh.
            if (!entry.isLiveAt(now())) {
                return null;
            }
            ResolvedLeaseConfig resolved = resolveConfig(creditTypeId);
            boolean belowWatermark = atOrBelowWatermark(entry, resolved);
            boolean belowRequired = requiredCredits != null && entry.getLocalRemainingCredits() < requiredCredits;
            if (!belowWatermark && !belowRequired) {
                return entry;
            }
            // Cover the triggering check, or one needing more than a tranche would never pass.
            double shortfall = requiredCredits != null ? requiredCredits - entry.getLocalRemainingCredits() : 0;
            double additionalAmount = Math.max(resolved.getLeaseSize(), shortfall);

            Flight inFlight = extendFlights.get(key);
            if (inFlight != null && (joinsLeft > 0 || additionalAmount <= inFlight.requestedAdditional)) {
                if (!joinInFlight) {
                    // A background caller doesn't read the result, so it doesn't park a thread.
                    return null;
                }
                inFlight.await(remaining(timeout, startedAt));
                if (!inFlight.isDone()) {
                    // The caller's wait timed out; the flight runs on for the others.
                    debug("An extend in flight for " + companyId + "/" + creditTypeId + " outlasted the caller's "
                            + "timeout; not waiting on it");
                    return null;
                }
                // A flight that sent enough serves us too. One that sent nothing covers nobody.
                if (inFlight.sentExtend && additionalAmount <= inFlight.requestedAdditional) {
                    // Read from the future rather than the wait: the flight can finish between a
                    // timed-out wait and the isDone check above.
                    return inFlight.result.getNow(null);
                }
                // Too small: re-read and size the next ask against the new balance.
                continue;
            }
            Duration budget = remaining(timeout, startedAt);
            if (budget != null && budget.isZero()) {
                // It would time out on the client while the server may still grant it.
                debug("Not extending a credit lease for " + companyId + "/" + creditTypeId
                        + ": the caller's timeout has run out");
                return null;
            }
            // Registered atomically against the flight this call saw: with a blind put, two
            // callers that both found the slot empty would each send an extend, and the server
            // would grant both.
            Flight flight = new Flight(additionalAmount);
            boolean registered = inFlight == null
                    ? extendFlights.putIfAbsent(key, flight) == null
                    : extendFlights.replace(key, inFlight, flight);
            if (!registered) {
                // Lost the race: go round and join the winner, without spending a join.
                joinsLeft++;
                continue;
            }
            return startExtend(
                    key, flight, companyId, creditTypeId, entry, resolved, requiredCredits, additionalAmount, budget);
        }
    }

    /**
     * Sends the extend for a flight the caller has already registered as the slot's own.
     * Deregistration is identity-guarded, so a flight this one took over cannot evict it on its
     * way out.
     */
    private LeaseState startExtend(
            String key,
            Flight flight,
            String companyId,
            String creditTypeId,
            LeaseState entry,
            ResolvedLeaseConfig resolved,
            Double requiredCredits,
            double additionalAmount,
            Duration timeout) {
        LeaseState result = null;
        try {
            boolean stoppedInTheGap;
            synchronized (stopLock) {
                // Same guard as acquire: the check at the top of maybeExtend can go stale before
                // the flight registers, and drain() may already have looked for it.
                stoppedInTheGap = stopped;
            }
            if (stoppedInTheGap) {
                debug("Not extending a credit lease for " + companyId + "/" + creditTypeId
                        + ": the manager stopped while the flight was being registered");
                return null;
            }
            // Re-read now the flight is ours: an earlier extend may have landed since.
            LeaseState fresh = stillNeedsExtending(companyId, creditTypeId, requiredCredits, resolved);
            if (fresh == null) {
                return leases.get(companyId, creditTypeId);
            }
            flight.sentExtend = true;
            result = extend(fresh, resolved, additionalAmount, timeout);
            return result;
        } catch (RuntimeException e) {
            warn("Failed to extend credit lease " + entry.getLeaseId() + ": " + e);
            return null;
        } finally {
            // Identity-guarded, so a flight that took this one over isn't evicted. Ordered as in
            // acquireIfNeeded.
            extendFlights.remove(key, flight);
            flight.result.complete(result);
        }
    }

    /**
     * Kicks off a water-mark extend without waiting for it: a check that just drew the lease down
     * should not pay for the top-up.
     */
    public void extendInBackground(String companyId, String creditTypeId) {
        // Tested on the caller's thread so the common case, nothing to do, spawns no task.
        if (extendFlights.containsKey(LeaseStore.leaseKey(companyId, creditTypeId))) {
            return;
        }
        if (!extendIsDue(companyId, creditTypeId)) {
            return;
        }
        spawn(() -> maybeExtend(companyId, creditTypeId, null, false, null));
    }

    /** Whether the slot's lease has drawn down far enough to warrant a steady-state top-up. */
    private boolean extendIsDue(String companyId, String creditTypeId) {
        if (stopped) {
            return false;
        }
        LeaseState entry;
        try {
            entry = leases.get(companyId, creditTypeId);
        } catch (RuntimeException e) {
            warn("Failed to read lease store for " + companyId + "/" + creditTypeId + ": " + e);
            return false;
        }
        return entry != null && entry.isLiveAt(now()) && atOrBelowWatermark(entry, resolveConfig(creditTypeId));
    }

    /**
     * The slot's row if it still warrants the extend the caller sized, null if it no longer does.
     * Read after winning the flight, so it reflects any extend that landed while this caller was
     * deciding.
     */
    private LeaseState stillNeedsExtending(
            String companyId, String creditTypeId, Double requiredCredits, ResolvedLeaseConfig resolved) {
        LeaseState fresh = leases.get(companyId, creditTypeId);
        if (fresh == null || !fresh.isLiveAt(now())) {
            return null;
        }
        boolean belowRequired = requiredCredits != null && fresh.getLocalRemainingCredits() < requiredCredits;
        return atOrBelowWatermark(fresh, resolved) || belowRequired ? fresh : null;
    }

    private static boolean atOrBelowWatermark(LeaseState entry, ResolvedLeaseConfig resolved) {
        double ratio = entry.getLocalRemainingCredits() / Math.max(entry.getGrantedAmount(), 1);
        return ratio <= resolved.getLowWaterMark();
    }

    private LeaseState extend(
            LeaseState entry, ResolvedLeaseConfig resolved, double additionalAmount, Duration timeout) {
        LeaseGrant grant;
        try {
            grant = wire.extend(entry.getLeaseId(), additionalAmount, now().plus(resolved.getLeaseDuration()), timeout);
        } catch (RuntimeException e) {
            warn("Failed to extend credit lease " + entry.getLeaseId() + ": " + e);
            return null;
        }
        try {
            // Reconcile to the server's total, since siblings may have extended too. Pinned to
            // this lease so an expiry mid-call can't credit a successor.
            leases.extend(
                    entry.getCompanyId(),
                    entry.getCreditTypeId(),
                    grant.getGrantedAmount(),
                    grant.getExpiresAt(),
                    entry.getLeaseId());
            debug("Extended credit lease " + entry.getLeaseId() + " to " + grant.getGrantedAmount() + " (was "
                    + entry.getGrantedAmount() + " at last read, expires " + grant.getExpiresAt() + ")");
            return leases.get(entry.getCompanyId(), entry.getCreditTypeId());
        } catch (RuntimeException e) {
            warn("Failed to reconcile extended credit lease " + entry.getLeaseId() + ": " + e);
            return null;
        }
    }

    /**
     * Releases every live lease this process exclusively holds, returning their unspent
     * remainders to the company balance immediately instead of waiting out the lease expiry.
     *
     * <p>Only a per-process store implements {@link LeaseLister}; a shared backend is skipped,
     * since sibling processes still draw on those leases. Expired leases are skipped too: the
     * server already swept them. Best-effort, with failures falling back to server-side expiry.
     */
    public void releaseAllLocalLeases() {
        releaseAllLocalLeases(CreditLeaseDefaults.SHUTDOWN_DRAIN_TIMEOUT);
    }

    /**
     * Releases every live lease this process exclusively holds, within {@code budget}.
     *
     * <p>The releases go out together and the budget bounds the whole set, not each one in turn.
     * Issued serially, a process holding many slots behind a slow server would stretch a shutdown
     * by the sum of them, and a single hung release would spend the entire budget on its own and
     * abandon every lease behind it. Whatever has not landed by the deadline is left to
     * server-side expiry, which is where a failed release leaves it too.
     */
    public void releaseAllLocalLeases(Duration budget) {
        if (!(leases instanceof LeaseLister)) {
            return;
        }
        List<LeaseState> entries;
        try {
            entries = ((LeaseLister) leases).list();
        } catch (RuntimeException e) {
            warn("Failed to enumerate leases on close: " + e);
            return;
        }
        long deadline = System.nanoTime() + Math.max(0, budget.toNanos());
        Instant now = now();
        List<CompletableFuture<?>> releases = new ArrayList<>();
        for (LeaseState entry : entries) {
            if (!entry.isLiveAt(now)) {
                continue;
            }
            CompletableFuture<Void> released = new CompletableFuture<>();
            try {
                executor.execute(() -> {
                    try {
                        wire.release(entry.getLeaseId());
                        leases.drop(entry.getCompanyId(), entry.getCreditTypeId());
                        debug("Released credit lease " + entry.getLeaseId() + " on close");
                    } catch (RuntimeException e) {
                        warn("Failed to release credit lease " + entry.getLeaseId() + " on close (it will expire "
                                + "server-side): " + e);
                    } finally {
                        released.complete(null);
                    }
                });
                releases.add(released);
            } catch (RejectedExecutionException e) {
                debug("Credit lease executor is shut down; leaving " + entry.getLeaseId() + " to server-side expiry");
            }
        }
        if (!awaitAll(releases, deadline)) {
            warn("Ran out of shutdown budget releasing credit leases; any still held will expire server-side");
        }
    }

    /**
     * Runs the expired-reservation sweep on the configured interval. Safe to call twice; a no-op
     * without a reservation store or after {@link #stop()}.
     */
    public void startSweep() {
        if (reservations == null || stopped || !sweeping.compareAndSet(false, true)) {
            return;
        }
        long interval = Math.max(1, sweepInterval.toMillis());
        sweeper.scheduleWithFixedDelay(
                () -> {
                    try {
                        reservations.sweepExpired();
                    } catch (RuntimeException e) {
                        // Keep the loop alive: a sweep failure is transient (a Redis blip), and
                        // the next tick retries.
                        debug("Reservation sweep failed: " + e);
                    }
                },
                interval,
                interval,
                TimeUnit.MILLISECONDS);
    }

    /**
     * Refuses new lease work. Idempotent, and paired with {@link #drain}: stopping first is what
     * makes the drain terminate, since nothing can queue behind it.
     */
    public void stop() {
        synchronized (stopLock) {
            stopped = true;
        }
        sweeper.shutdownNow();
    }

    /**
     * Waits out lease work already on the wire, so a close releases what that work installs
     * instead of orphaning it. Bounded: whatever has not landed by {@code timeout} is abandoned
     * rather than stalling the caller's shutdown, and the credits it holds fall back to
     * server-side expiry.
     */
    public void drain(Duration timeout) {
        long deadline = System.nanoTime() + Math.max(0, timeout.toNanos());
        while (true) {
            List<CompletableFuture<?>> pending = new ArrayList<>(background);
            for (Flight flight : acquireFlights.values()) {
                pending.add(flight.result);
            }
            for (Flight flight : extendFlights.values()) {
                pending.add(flight.result);
            }
            if (pending.isEmpty()) {
                return;
            }
            // Settling one round can queue another (an acquire that loses its race fires a
            // release), so keep going until nothing is left.
            if (!awaitAll(pending, deadline)) {
                warn("Timed out after " + timeout.toMillis() + "ms draining in-flight credit lease work; any "
                        + "credits it holds will be released by server-side expiry");
                return;
            }
        }
    }

    private boolean awaitAll(List<CompletableFuture<?>> pending, long deadlineNanos) {
        for (CompletableFuture<?> future : pending) {
            if (!await(future, deadlineNanos)) {
                return false;
            }
        }
        return true;
    }

    private boolean await(CompletableFuture<?> future, long deadlineNanos) {
        long remaining = deadlineNanos - System.nanoTime();
        if (remaining <= 0) {
            return false;
        }
        try {
            future.get(remaining, TimeUnit.NANOSECONDS);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (TimeoutException e) {
            return false;
        } catch (RuntimeException | ExecutionException e) {
            // A failed background step has already logged; the drain only cares that it landed.
            return true;
        }
    }

    /** Stops the manager and drains what it has in flight. Leases are released by the caller. */
    @Override
    public void close() {
        close(CreditLeaseDefaults.SHUTDOWN_DRAIN_TIMEOUT);
    }

    /**
     * Stops the manager and drains what it has in flight within {@code budget}. Leases are
     * released by the caller.
     *
     * <p>A caller closing several components under one deadline passes what is left of it, so the
     * bound it promised is not reset to a full drain timeout here.
     */
    public void close(Duration budget) {
        stop();
        drain(budget);
        executor.shutdown();
    }

    /** Runs a fire-and-forget step, refused once the manager is stopped. */
    private void spawn(Runnable step) {
        spawn(step, false);
    }

    /**
     * Runs a fire-and-forget step. {@code afterStop} keeps work that has to happen even once the
     * manager is stopping, which is what the drain is there to wait out; everything else is
     * refused after {@link #stop()}, where it would touch a manager being torn down. Nothing here
     * lets an exception escape: these paths are unawaited, so there is nobody to catch for them.
     */
    private void spawn(Runnable step, boolean afterStop) {
        if (stopped && !afterStop) {
            return;
        }
        CompletableFuture<Void> landed = new CompletableFuture<>();
        background.add(landed);
        try {
            executor.execute(() -> {
                try {
                    step.run();
                } catch (RuntimeException e) {
                    error("Background credit lease work failed: " + e);
                } finally {
                    // Removed before completing, as with flights.
                    background.remove(landed);
                    landed.complete(null);
                }
            });
        } catch (RejectedExecutionException e) {
            background.remove(landed);
            debug("Credit lease executor is shut down; skipping background work");
        }
    }

    /** What is left of {@code timeout} since {@code startedAt}, or null for no timeout. */
    private static Duration remaining(Duration timeout, long startedAt) {
        if (timeout == null) {
            return null;
        }
        Duration left = timeout.minusNanos(System.nanoTime() - startedAt);
        return left.isNegative() ? Duration.ZERO : left;
    }

    private Instant now() {
        return clock.instant();
    }

    private static String orElse(String value, String fallback) {
        return value != null && !value.isEmpty() ? value : fallback;
    }

    private static ThreadFactory daemonThreads(String name) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, name);
                thread.setDaemon(true);
                return thread;
            }
        };
    }

    private void debug(String message) {
        if (logger != null) {
            logger.debug(message);
        }
    }

    private void warn(String message) {
        if (logger != null) {
            logger.warn(message);
        }
    }

    private void error(String message) {
        if (logger != null) {
            logger.error(message);
        }
    }

    /** One in-flight wire call for a slot, and the amount its extend asked the server for. */
    private final class Flight {

        private final double requestedAdditional;
        private final CompletableFuture<LeaseState> result = new CompletableFuture<>();
        // False when the flight re-read the slot and found no extend needed.
        private volatile boolean sentExtend;

        Flight(double requestedAdditional) {
            this.requestedAdditional = requestedAdditional;
        }

        boolean isDone() {
            return result.isDone();
        }

        /**
         * The flight's answer, waited for no longer than the joining caller's deadline (null for
         * none). Giving up leaves the flight running for the other callers.
         */
        LeaseState await(Duration timeout) {
            try {
                return timeout == null
                        ? result.get()
                        : result.get(Math.max(1, timeout.toMillis()), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } catch (ExecutionException e) {
                return null;
            } catch (TimeoutException e) {
                debug("Gave up waiting " + timeout.toMillis() + "ms on an in-flight credit lease call; it "
                        + "continues for the callers still on it");
                return null;
            }
        }
    }
}
