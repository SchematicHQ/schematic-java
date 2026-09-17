package com.schematic.api.credits;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps the reservation table in this process, and refunds into the lease store it is handed.
 * Swap in {@link RedisReservationStore} to share holds across processes.
 */
public final class InMemoryReservationStore implements ReservationStore {

    private final ReservationRefunder leases;
    private final Clock clock;
    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();

    public InMemoryReservationStore(ReservationRefunder leases) {
        this(leases, Clock.systemUTC());
    }

    public InMemoryReservationStore(ReservationRefunder leases, Clock clock) {
        this.leases = leases;
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    @Override
    public void add(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    @Override
    public Reservation get(String id) {
        return reservations.get(id);
    }

    @Override
    public Double consume(String id, double creditsConsumed) {
        // remove() is the claim: of two racing callers exactly one comes away with the record.
        Reservation reservation = reservations.remove(id);
        if (reservation == null) {
            return null;
        }
        double consumed = CreditAmounts.clampConsumption(creditsConsumed, reservation.getCreditsReserved());
        double refund = reservation.getCreditsReserved() - consumed;
        if (refund > 0) {
            // Pinned to the originating lease: if that lease has expired and a successor holds
            // the slot, the refund is dropped, because the expired lease's remainder already went
            // back to the company balance server-side.
            leases.refund(reservation.getCompanyId(), reservation.getCreditTypeId(), refund, reservation.getLeaseId());
        }
        return consumed;
    }

    @Override
    public double reservedCredits(String companyId, String creditTypeId) {
        double total = 0;
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCompanyId().equals(companyId)
                    && reservation.getCreditTypeId().equals(creditTypeId)) {
                total += reservation.getCreditsReserved();
            }
        }
        return total;
    }

    @Override
    public int sweepExpired() {
        Instant cutoff = clock.instant();
        List<String> expired = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (!reservation.getExpiresAt().isAfter(cutoff)) {
                expired.add(reservation.getId());
            }
        }
        int swept = 0;
        for (String id : expired) {
            // Routed through consume so the sweep claims exactly once too.
            if (consume(id, 0) != null) {
                swept++;
            }
        }
        return swept;
    }

    @Override
    public int count() {
        return reservations.size();
    }
}
