package com.schematic.api.credits;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.schematic.api.Schematic;
import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.logger.SchematicLogger;
import com.schematic.api.types.EventBodyTrack;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * A settle bumps the cached company metrics once per reservation, whatever happened locally. The
 * server bills the first settle and drops a repeat as a duplicate, so the local count follows it.
 */
class TrackWithReservationMetricsTest {

    @Test
    void aSettleThatClaimedTheHoldMovesTheCachedMetrics() {
        DataStreamClient dataStream = mock(DataStreamClient.class);

        try (Schematic schematic = client(dataStream)) {
            Reservation reservation = reservation(CreditLeaseMode.CLIENT);
            reservations(schematic).add(reservation);

            schematic.trackWithReservation(reservation, 3);

            verify(dataStream).updateCompanyMetrics(any(EventBodyTrack.class));
        }
    }

    @Test
    void aSettleOfAnExpiredHoldStillMovesTheCachedMetrics() {
        DataStreamClient dataStream = mock(DataStreamClient.class);

        try (Schematic schematic = client(dataStream)) {
            // Never added to the store, as if swept at its TTL: nothing to claim locally, but the
            // event bills the usage for the first time.
            schematic.trackWithReservation(reservation(CreditLeaseMode.CLIENT), 3);

            verify(dataStream).updateCompanyMetrics(any(EventBodyTrack.class));
        }
    }

    @Test
    void aRepeatedSettleMovesTheCachedMetricsOnce() {
        DataStreamClient dataStream = mock(DataStreamClient.class);

        try (Schematic schematic = client(dataStream)) {
            Reservation client = reservation(CreditLeaseMode.CLIENT);
            reservations(schematic).add(client);
            schematic.trackWithReservation(client, 3);
            schematic.trackWithReservation(client, 3);

            Reservation server = reservation(CreditLeaseMode.SERVER);
            schematic.trackWithReservation(server, 3);
            schematic.trackWithReservation(server, 3);

            verify(dataStream, times(2)).updateCompanyMetrics(any(EventBodyTrack.class));
        }
    }

    private static Schematic client(DataStreamClient dataStream) {
        Schematic schematic = Schematic.builder()
                .apiKey("test_api_key")
                .logger(mock(SchematicLogger.class))
                .creditLeases(
                        CreditLeaseConfig.builder().mode(CreditLeaseMode.CLIENT).build())
                .build();
        // The builder only wires a DataStream from a live socket, and the metrics update is the
        // one thing on this path that needs one.
        set(schematic, "dataStreamClient", dataStream);
        return schematic;
    }

    private static Reservation reservation(CreditLeaseMode mode) {
        return new Reservation(
                UUID.randomUUID().toString(),
                "lse_1",
                mode,
                "comp_1",
                "bilcr_1",
                "tokens",
                10,
                10,
                1,
                Instant.now().plusSeconds(300),
                Collections.singletonMap("company_id", "acme"),
                null);
    }

    private static ReservationStore reservations(Schematic schematic) {
        return (ReservationStore) read(schematic, "reservations");
    }

    private static void set(Schematic schematic, String name, Object value) {
        try {
            Field field = Schematic.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(schematic, value);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static Object read(Schematic schematic, String name) {
        try {
            Field field = Schematic.class.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(schematic);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }
}
