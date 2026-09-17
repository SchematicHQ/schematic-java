package com.schematic.api.credits.conformance;

import com.schematic.api.credits.LeaseGrant;
import com.schematic.api.credits.LeaseState;
import com.schematic.api.credits.LeaseStore;
import com.schematic.api.credits.ReserveResult;
import java.time.Instant;

/**
 * A lease store whose refund fails once while armed, reproducing a process death between a
 * reservation's claim and its refund. The reservation store refunds through whatever store it is
 * handed, so wrapping that one leaves the rest of the vector reading the real store.
 */
final class CrashingRefundLeaseStore implements LeaseStore {

    static final class SimulatedCrash extends RuntimeException {
        SimulatedCrash() {
            super("simulated crash before refund");
        }
    }

    private final LeaseStore target;
    private volatile boolean armed;

    CrashingRefundLeaseStore(LeaseStore target) {
        this.target = target;
    }

    void arm() {
        armed = true;
    }

    @Override
    public LeaseState get(String companyId, String creditTypeId) {
        return target.get(companyId, creditTypeId);
    }

    @Override
    public boolean replace(LeaseGrant grant) {
        return target.replace(grant);
    }

    @Override
    public ReserveResult tryReserve(String companyId, String creditTypeId, double credits) {
        return target.tryReserve(companyId, creditTypeId, credits);
    }

    @Override
    public void refund(String companyId, String creditTypeId, double credits, String pinLeaseId) {
        if (armed) {
            armed = false;
            throw new SimulatedCrash();
        }
        target.refund(companyId, creditTypeId, credits, pinLeaseId);
    }

    @Override
    public void extend(
            String companyId, String creditTypeId, double grantedTotal, Instant newExpiresAt, String pinLeaseId) {
        target.extend(companyId, creditTypeId, grantedTotal, newExpiresAt, pinLeaseId);
    }

    @Override
    public void drop(String companyId, String creditTypeId) {
        target.drop(companyId, creditTypeId);
    }
}
