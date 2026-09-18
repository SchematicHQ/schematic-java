package com.schematic.api.credits;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One credit hold, issued by {@code check()} and settled by {@code trackWithReservation()}.
 *
 * <p>In client mode the hold is a local carve-out of a lease; in server mode the API holds the
 * credits and the settling track event routes by reservation id.
 */
public final class Reservation {

    private final String id;
    private final String leaseId;
    private final CreditLeaseMode mode;
    private final String companyId;
    private final String creditTypeId;
    private final String eventSubtype;
    private final double quantityReserved;
    private final double creditsReserved;
    private final double consumptionRate;
    private final Instant expiresAt;
    private final Map<String, String> company;
    private final Map<String, String> user;

    public Reservation(
            String id,
            String leaseId,
            CreditLeaseMode mode,
            String companyId,
            String creditTypeId,
            String eventSubtype,
            double quantityReserved,
            double creditsReserved,
            double consumptionRate,
            Instant expiresAt,
            Map<String, String> company,
            Map<String, String> user) {
        this.id = id;
        this.leaseId = leaseId;
        this.mode = mode;
        this.companyId = companyId;
        this.creditTypeId = creditTypeId;
        this.eventSubtype = eventSubtype;
        this.quantityReserved = quantityReserved;
        this.creditsReserved = creditsReserved;
        this.consumptionRate = consumptionRate;
        this.expiresAt = expiresAt;
        this.company = copy(company);
        this.user = copy(user);
    }

    private static Map<String, String> copy(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(keys));
    }

    /** Opaque reservation id. */
    public String getId() {
        return id;
    }

    /**
     * The lease this hold was carved from, which pins its refunds. In server mode there is no
     * lease, so this mirrors {@link #getId()} and the field stays populated.
     */
    public String getLeaseId() {
        return leaseId;
    }

    /** Where the hold lives. */
    public CreditLeaseMode getMode() {
        return mode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCreditTypeId() {
        return creditTypeId;
    }

    /** The event the settling track is billed as. */
    public String getEventSubtype() {
        return eventSubtype;
    }

    /** The caller-declared usage, in event units. */
    public double getQuantityReserved() {
        return quantityReserved;
    }

    /**
     * {@code ceil(quantityReserved) * consumptionRate}. Whole event units, since a fraction of an
     * event is not something the server bills, so this is what the settle will charge.
     */
    public double getCreditsReserved() {
        return creditsReserved;
    }

    /** The consumption rate at the time the hold was issued. */
    public double getConsumptionRate() {
        return consumptionRate;
    }

    /** When the hold expires and is swept back to the lease. */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * The company keys the hold was issued for, threaded onto the track event so the server
     * attributes the usage to the same company. Null when the check named none.
     */
    public Map<String, String> getCompany() {
        return company;
    }

    /** The user keys the hold was issued for. Null when the check named none. */
    public Map<String, String> getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Reservation{id=" + id
                + ", leaseId=" + leaseId
                + ", mode=" + mode
                + ", companyId=" + companyId
                + ", creditTypeId=" + creditTypeId
                + ", eventSubtype=" + eventSubtype
                + ", quantityReserved=" + quantityReserved
                + ", creditsReserved=" + creditsReserved
                + ", consumptionRate=" + consumptionRate
                + ", expiresAt=" + expiresAt
                + "}";
    }
}
