package com.schematic.api.credits;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * A settle moves the cached company metrics only when it moved local state with it. The event is
 * keyed off the reservation id, so the server drops a retried settle as a duplicate: bumping the
 * cached metric for one would have the caller's next local evaluation gate on usage counted twice.
 */
class TrackWithReservationMetricsTest {

    @Test
    void aSettleThatClaimedTheHoldMovesTheCachedMetrics() {
        DataStreamClient dataStream = mock(DataStreamClient.class);
        when(dataStream.isConnected()).thenReturn(true);

        try (Schematic schematic = client(dataStream)) {
            Reservation reservation = reservation();
            reservations(schematic).add(reservation);

            schematic.trackWithReservation(reservation, 3);

            verify(dataStream).updateCompanyMetrics(any(EventBodyTrack.class));
        }
    }

    @Test
    void aSettleThatDidNotSettleLocallyLeavesTheCachedMetricsAlone() {
        DataStreamClient dataStream = mock(DataStreamClient.class);
        // Lenient because reaching the connection check at all is the regression this guards.
        lenient().when(dataStream.isConnected()).thenReturn(true);

        try (Schematic schematic = client(dataStream)) {
            // The hold was never added to the store, so the settle claims nothing: the state a
            // retried settle, an expired hold or an unreachable store all leave behind.
            schematic.trackWithReservation(reservation(), 3);

            verify(dataStream, never()).updateCompanyMetrics(any(EventBodyTrack.class));
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

    private static Reservation reservation() {
        return new Reservation(
                UUID.randomUUID().toString(),
                "lse_1",
                CreditLeaseMode.CLIENT,
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
