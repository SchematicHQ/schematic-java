package com.schematic.api.credits.conformance;

import com.schematic.api.credits.LeaseGrant;
import com.schematic.api.credits.LeaseWireClient;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** Stands in for the lease API: queued responses in, recorded calls out. */
final class ScriptedWireClient implements LeaseWireClient {

    /** One acquire or extend the vector scripts the server to answer with. */
    static final class Script {

        final String leaseId;
        final double grantedAmount;
        final Instant expiresAt;
        final String error;

        private Script(String leaseId, double grantedAmount, Instant expiresAt, String error) {
            this.leaseId = leaseId;
            this.grantedAmount = grantedAmount;
            this.expiresAt = expiresAt;
            this.error = error;
        }

        static Script lease(String leaseId, double grantedAmount, Instant expiresAt) {
            return new Script(leaseId, grantedAmount, expiresAt, null);
        }

        static Script error(String message) {
            return new Script(null, 0, null, message);
        }
    }

    static final class AcquireCall {

        final String companyId;
        final String creditTypeId;
        final double requestedAmount;
        final Instant expiresAt;

        AcquireCall(String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
            this.companyId = companyId;
            this.creditTypeId = creditTypeId;
            this.requestedAmount = requestedAmount;
            this.expiresAt = expiresAt;
        }
    }

    static final class ExtendCall {

        final String leaseId;
        final double additionalAmount;
        final Instant expiresAt;

        ExtendCall(String leaseId, double additionalAmount, Instant expiresAt) {
            this.leaseId = leaseId;
            this.additionalAmount = additionalAmount;
            this.expiresAt = expiresAt;
        }
    }

    private final Deque<Script> acquireResponses = new ArrayDeque<>();
    private final Deque<Script> extendResponses = new ArrayDeque<>();
    final List<AcquireCall> acquireCalls = new ArrayList<>();
    final List<ExtendCall> extendCalls = new ArrayList<>();
    final List<String> releasedLeaseIds = new ArrayList<>();

    /** Runs while an acquire is in flight, for emulating a sibling process winning the race. */
    Runnable duringAcquire;

    void queueAcquire(Script script) {
        acquireResponses.add(script);
    }

    void queueExtend(Script script) {
        extendResponses.add(script);
    }

    @Override
    public synchronized LeaseGrant acquire(
            String companyId, String creditTypeId, double requestedAmount, Instant expiresAt) {
        acquireCalls.add(new AcquireCall(companyId, creditTypeId, requestedAmount, expiresAt));
        if (duringAcquire != null) {
            Runnable hook = duringAcquire;
            duringAcquire = null;
            hook.run();
        }
        Script script = acquireResponses.poll();
        if (script == null) {
            throw new IllegalStateException("no scripted acquire response for " + companyId + "/" + creditTypeId);
        }
        if (script.error != null) {
            throw new IllegalStateException(script.error);
        }
        return new LeaseGrant(script.leaseId, companyId, creditTypeId, script.grantedAmount, script.expiresAt);
    }

    @Override
    public synchronized LeaseGrant extend(String leaseId, double additionalAmount, Instant expiresAt) {
        extendCalls.add(new ExtendCall(leaseId, additionalAmount, expiresAt));
        Script script = extendResponses.poll();
        if (script == null) {
            throw new IllegalStateException("no scripted extend response for " + leaseId);
        }
        if (script.error != null) {
            throw new IllegalStateException(script.error);
        }
        return new LeaseGrant(leaseId, null, null, script.grantedAmount, script.expiresAt);
    }

    @Override
    public synchronized void release(String leaseId) {
        releasedLeaseIds.add(leaseId);
    }
}
