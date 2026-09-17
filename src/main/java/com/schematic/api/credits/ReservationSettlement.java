package com.schematic.api.credits;

import com.schematic.api.types.EventBodyTrack;

/** Consumes a client-mode hold against its lease and builds the event that bills it. */
public final class ReservationSettlement {

    /** What a settle did locally, and what it owes the server. */
    public static final class SettleOutcome {
        private final EventBodyTrack track;
        private final boolean settledLocally;

        SettleOutcome(EventBodyTrack track, boolean settledLocally) {
            this.track = track;
            this.settledLocally = settledLocally;
        }

        /** The billing event to emit. */
        public EventBodyTrack getTrack() {
            return track;
        }

        /**
         * True when the hold was still open and this call debited the consumed slice and refunded
         * the rest. False when it had already been swept at its TTL, already settled, or the store
         * was unreachable: the lease balance was not touched here, so it reads high until the
         * lease rolls over, and the event is a recovery emit.
         */
        public boolean isSettledLocally() {
            return settledLocally;
        }
    }

    private ReservationSettlement() {}

    /**
     * Settles a hold and builds its billing event.
     *
     * <p>The event comes from the caller-held reservation rather than the store, so the usage is
     * still billed once the hold has been swept. Only the local bookkeeping clamps to the reserved
     * amount; the event carries the unclamped actual.
     */
    public static SettleOutcome settle(ReservationStore reservations, Reservation reservation, double actualQuantity) {
        Double claimed = reservations.consume(reservation.getId(), actualQuantity * reservation.getConsumptionRate());
        return new SettleOutcome(buildTrackEvent(reservation, actualQuantity), claimed != null);
    }

    /**
     * Builds the event that settles a hold, from the reservation alone.
     *
     * <p>Kept free of store access so the client can still bill the usage when the local settle
     * fails against an unreachable store. In client mode the lease id routes the server-side
     * consumption through the lease's sub-ledger instead of decrementing a grant the acquire
     * already pre-debited; in server mode the hold lives on the server and settles by its own id,
     * and the lease id is never sent, since the server prefers it when both are set.
     */
    public static EventBodyTrack buildTrackEvent(Reservation reservation, double actualQuantity) {
        EventBodyTrack._FinalStage event =
                EventBodyTrack.builder().event(reservation.getEventSubtype()).quantity(settleQuantity(actualQuantity));
        if (reservation.getMode() == CreditLeaseMode.SERVER) {
            event.reservationId(reservation.getId());
        } else {
            event.leaseId(reservation.getLeaseId());
        }
        if (reservation.getCompany() != null) {
            event.company(reservation.getCompany());
        }
        if (reservation.getUser() != null) {
            event.user(reservation.getUser());
        }
        return event.build();
    }

    /**
     * Casts a settled usage onto the integer a track event records. A hold can be sized from a
     * fractional usage, but the event's quantity is an integer, so a partial unit settles as a
     * whole one rather than as none.
     */
    public static long settleQuantity(double actualQuantity) {
        return (long) Math.ceil(actualQuantity);
    }
}
