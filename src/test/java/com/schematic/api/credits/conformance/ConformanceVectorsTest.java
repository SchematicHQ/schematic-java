package com.schematic.api.credits.conformance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schematic.api.credits.CheckRequest;
import com.schematic.api.credits.CheckResult;
import com.schematic.api.credits.CreditCheck;
import com.schematic.api.credits.CreditLeaseConfig;
import com.schematic.api.credits.CreditLeaseDefaults;
import com.schematic.api.credits.CreditLeaseManager;
import com.schematic.api.credits.CreditLeaseMode;
import com.schematic.api.credits.LeaseGrant;
import com.schematic.api.credits.LeaseLister;
import com.schematic.api.credits.LeaseState;
import com.schematic.api.credits.PreflightOptions;
import com.schematic.api.credits.Reservation;
import com.schematic.api.credits.ReservationSettlement;
import com.schematic.api.credits.ReserveResult;
import com.schematic.api.types.EventBodyTrack;
import com.schematic.api.types.RulesengineFeatureEntitlement;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/**
 * Runs the language-agnostic conformance vectors against this SDK.
 *
 * <p>The vectors and the semantics they pin live in {@code conformance/} at the repo root, copied
 * verbatim from schematic-node, the reference implementation. This runner is the only
 * language-specific piece; every port reimplements it and must pass the same vectors, on every
 * store backend it ships.
 *
 * <p>The vectors are grouped by what they drive, so a failure names the layer: the stores, the
 * lease manager against a scripted wire client, or the whole check and track flow against a
 * scripted rules engine.
 */
class ConformanceVectorsTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<String> BACKENDS = Arrays.asList(Backend.IN_MEMORY, Backend.REDIS);

    private static final Set<String> STORE_CATEGORIES =
            new HashSet<>(Arrays.asList("lease_lifecycle", "reservation_lifecycle", "expiry", "crash_window"));

    private static final Duration DRAIN_TIMEOUT = Duration.ofSeconds(5);

    private static final Set<String> MANAGER_CATEGORIES = new HashSet<>(Collections.singletonList("lease_manager"));

    private static final Set<String> FLOW_CATEGORIES =
            new HashSet<>(Arrays.asList("check_flow", "track_settle", "fractional_usage"));

    private static final Set<String> RESERVATION_EXPECT_KEYS = keys(
            "lease_id", "credit_type_id", "event_subtype", "quantity_reserved", "credits_reserved", "consumption_rate");

    private static final Set<String> TRACK_EXPECT_KEYS = keys("event", "quantity", "lease_id", "reservation_id");

    private static final Set<String> ENGINE_CALL_EXPECT_KEYS =
            keys("credit_balance", "credit_cost", "event_usage", "usage");

    private static final Set<String> EVENT_USAGE_EXPECT_KEYS = keys("event_subtype", "quantity");

    /**
     * What each op asserts. A vector re-synced from the reference implementation can carry an
     * expectation this runner has never heard of; without this map the vector would pass while
     * asserting nothing, so an unrecognised key fails the same way an unknown op does.
     */
    private static final Map<String, Set<String>> EXPECT_KEYS = expectKeys();

    private static Map<String, Set<String>> expectKeys() {
        Map<String, Set<String>> byOp = new HashMap<>();
        byOp.put("advance_clock", keys());
        byOp.put("replace_lease", keys("written"));
        byOp.put("drop_lease", keys());
        byOp.put("try_reserve", keys("balance", "lease_id"));
        byOp.put("refund_lease", keys());
        byOp.put("extend_lease", keys());
        byOp.put("get_lease", keys("exists", "lease_id", "granted_amount", "local_remaining_credits"));
        byOp.put("add_reservation", keys());
        byOp.put("consume_reservation", keys("consumed", "throws"));
        byOp.put("get_reservation", keys("exists"));
        byOp.put("reserved_credits", keys("total"));
        byOp.put("reservation_count", keys("count"));
        byOp.put(
                "check",
                keys(
                        "allowed",
                        "reason",
                        "err",
                        "has_reservation",
                        "fallback_called",
                        "reservation",
                        "engine_calls",
                        "wire_extends",
                        "last_extend_additional_amount"));
        byOp.put("track", keys("settled_locally", "track"));
        byOp.put(
                "acquire_if_needed",
                keys("lease_id", "wire_acquires", "last_acquire_requested_amount", "released_lease_ids"));
        byOp.put("maybe_extend", keys("wire_extends", "last_extend_additional_amount", "last_extend_lease_id"));
        byOp.put("release_all_local_leases", keys("released_lease_ids", "remaining_slots"));
        byOp.put("sweep_expired", keys("swept"));
        return byOp;
    }

    private static Set<String> keys(String... names) {
        return new HashSet<>(Arrays.asList(names));
    }

    @TestFactory
    Collection<DynamicTest> storeVectors() {
        return vectorsFor(STORE_CATEGORIES);
    }

    @TestFactory
    Collection<DynamicTest> managerVectors() {
        return vectorsFor(MANAGER_CATEGORIES);
    }

    @TestFactory
    Collection<DynamicTest> flowVectors() {
        return vectorsFor(FLOW_CATEGORIES);
    }

    /** Every vector file has to be claimed by a group, or it would silently never run. */
    @Test
    void everyCategoryIsCovered() {
        Set<String> claimed = new HashSet<>(STORE_CATEGORIES);
        claimed.addAll(MANAGER_CATEGORIES);
        claimed.addAll(FLOW_CATEGORIES);
        for (JsonNode document : loadDocuments()) {
            String category = document.get("category").asText();
            assertTrue(claimed.contains(category), "conformance category not claimed by any group: " + category);
        }
    }

    private Collection<DynamicTest> vectorsFor(Set<String> categories) {
        List<DynamicTest> tests = new ArrayList<>();
        for (JsonNode document : loadDocuments()) {
            String category = document.get("category").asText();
            if (!categories.contains(category)) {
                continue;
            }
            for (JsonNode vector : document.get("vectors")) {
                for (String backend : BACKENDS) {
                    if (!runsOn(vector, backend)) {
                        continue;
                    }
                    String name = backend + " - " + category + " - "
                            + vector.get("name").asText();
                    tests.add(dynamicTest(name, () -> runVector(backend, vector)));
                }
            }
        }
        assertFalse(tests.isEmpty(), "no conformance vectors found for " + categories);
        return tests;
    }

    private static boolean runsOn(JsonNode vector, String backend) {
        JsonNode backends = vector.get("backends");
        if (backends == null || !backends.isArray()) {
            return true;
        }
        for (JsonNode allowed : backends) {
            if (allowed.asText().equals(backend)) {
                return true;
            }
        }
        return false;
    }

    private static List<JsonNode> loadDocuments() {
        File directory = new File("conformance/vectors");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        assertNotNull(files, "conformance vectors directory not found at " + directory.getAbsolutePath());
        Arrays.sort(files);
        List<JsonNode> documents = new ArrayList<>();
        for (File file : files) {
            try {
                documents.add(MAPPER.readTree(file));
            } catch (IOException e) {
                throw new IllegalStateException("failed to parse " + file, e);
            }
        }
        return documents;
    }

    private void runVector(String backendName, JsonNode vector) {
        Harness harness = new Harness(Backend.create(backendName), vector.get("given"));
        String vectorName = vector.get("name").asText();
        try {
            harness.installGivenLeases();
            for (JsonNode op : vector.get("operations")) {
                runOperation(harness, op, vectorName);
            }
        } finally {
            harness.close();
        }
    }

    private void runOperation(Harness h, JsonNode op, String vectorName) {
        String name = op.get("op").asText();
        JsonNode expect = op.has("expect") ? op.get("expect") : MAPPER.createObjectNode();
        switch (name) {
            case "advance_clock":
                h.backend.clock.advance(op.get("ms").asLong());
                break;
            case "replace_lease":
                opReplaceLease(h, op, expect);
                break;
            case "drop_lease":
                h.backend.leases.drop(text(op, "company_id"), text(op, "credit_type_id"));
                break;
            case "try_reserve":
                opTryReserve(h, op, expect);
                break;
            case "refund_lease":
                h.backend.leases.refund(
                        text(op, "company_id"),
                        text(op, "credit_type_id"),
                        op.get("credits").asDouble(),
                        text(op, "pin_lease_id"));
                break;
            case "extend_lease":
                h.backend.leases.extend(
                        text(op, "company_id"),
                        text(op, "credit_type_id"),
                        op.get("granted_total").asDouble(),
                        op.has("expires_at_ms")
                                ? h.backend.clock.at(op.get("expires_at_ms").asDouble())
                                : null,
                        text(op, "pin_lease_id"));
                break;
            case "get_lease":
                opGetLease(h, op, expect);
                break;
            case "add_reservation":
                opAddReservation(h, op);
                break;
            case "consume_reservation":
                opConsumeReservation(h, op, expect);
                break;
            case "get_reservation":
                assertEquals(
                        expect.get("exists").asBoolean(),
                        h.backend.reservations.get(h.resolveReservationId(op)) != null,
                        "get_reservation exists");
                break;
            case "reserved_credits":
                assertEquals(
                        expect.get("total").asDouble(),
                        h.backend.reservations.reservedCredits(text(op, "company_id"), text(op, "credit_type_id")),
                        "reserved_credits total");
                break;
            case "reservation_count":
                assertEquals(expect.get("count").asInt(), h.backend.reservations.count(), "reservation_count");
                break;
            case "check":
                opCheck(h, op, expect);
                break;
            case "track":
                opTrack(h, op, expect);
                break;
            case "acquire_if_needed":
                opAcquireIfNeeded(h, op, expect);
                break;
            case "maybe_extend":
                opMaybeExtend(h, op, expect);
                break;
            case "release_all_local_leases":
                opReleaseAllLocalLeases(h, expect);
                break;
            case "sweep_expired":
                int swept = h.backend.reservations.sweepExpired();
                if (expect.has("swept")) {
                    assertEquals(expect.get("swept").asInt(), swept, "sweep_expired swept");
                }
                break;
            default:
                fail("unknown conformance op: " + name);
        }
        assertExpectHandled(name, expect, vectorName);
    }

    /** Fails on an expectation key no assertion above consumed, naming the key and the vector. */
    private static void assertExpectHandled(String op, JsonNode expect, String vectorName) {
        Set<String> known = EXPECT_KEYS.get(op);
        assertNotNull(known, "conformance op " + op + " declares no expectation keys");
        assertHandledKeys(expect, known, "expect", op, vectorName);
        assertHandledKeys(expect.get("reservation"), RESERVATION_EXPECT_KEYS, "expect.reservation", op, vectorName);
        assertHandledKeys(expect.get("track"), TRACK_EXPECT_KEYS, "expect.track", op, vectorName);
        JsonNode calls = expect.get("engine_calls");
        if (calls == null || !calls.isArray()) {
            return;
        }
        for (int i = 0; i < calls.size(); i++) {
            String where = "expect.engine_calls[" + i + "]";
            assertHandledKeys(calls.get(i), ENGINE_CALL_EXPECT_KEYS, where, op, vectorName);
            assertHandledKeys(
                    calls.get(i).get("event_usage"), EVENT_USAGE_EXPECT_KEYS, where + ".event_usage", op, vectorName);
        }
    }

    private static void assertHandledKeys(
            JsonNode node, Set<String> known, String where, String op, String vectorName) {
        if (node == null || !node.isObject()) {
            return;
        }
        List<String> unhandled = new ArrayList<>();
        Iterator<String> fields = node.fieldNames();
        while (fields.hasNext()) {
            String field = fields.next();
            if (!known.contains(field)) {
                unhandled.add(field);
            }
        }
        if (!unhandled.isEmpty()) {
            fail("unhandled conformance expectation " + unhandled + " in " + where + " of op " + op + " in vector "
                    + vectorName);
        }
    }

    private static void opReplaceLease(Harness h, JsonNode op, JsonNode expect) {
        boolean wrote = h.backend.leases.replace(new LeaseGrant(
                text(op, "lease_id"),
                text(op, "company_id"),
                text(op, "credit_type_id"),
                op.get("granted_amount").asDouble(),
                h.backend.clock.at(op.get("expires_at_ms").asDouble())));
        if (expect.has("written")) {
            assertEquals(expect.get("written").asBoolean(), wrote, "replace_lease written");
        }
    }

    private static void opTryReserve(Harness h, JsonNode op, JsonNode expect) {
        ReserveResult result = h.backend.leases.tryReserve(
                text(op, "company_id"),
                text(op, "credit_type_id"),
                op.get("credits").asDouble());
        if (expect.has("balance")) {
            if (expect.get("balance").isNull()) {
                assertNull(result, "try_reserve should have been refused");
            } else {
                assertNotNull(result, "try_reserve should have succeeded");
                assertEquals(expect.get("balance").asDouble(), result.getBalance(), "try_reserve balance");
            }
        }
        // The charged lease is what a caller pins its reservation to, so a vector that names one
        // is checking the pin, not just the arithmetic.
        if (expect.has("lease_id")) {
            if (expect.get("lease_id").isNull()) {
                assertNull(result, "try_reserve should have been refused");
            } else {
                assertNotNull(result, "try_reserve should have succeeded");
                assertEquals(expect.get("lease_id").asText(), result.getLeaseId(), "try_reserve lease_id");
            }
        }
    }

    private static void opGetLease(Harness h, JsonNode op, JsonNode expect) {
        LeaseState entry = h.backend.leases.get(text(op, "company_id"), text(op, "credit_type_id"));
        if (expect.has("exists")) {
            assertEquals(expect.get("exists").asBoolean(), entry != null, "get_lease exists");
        }
        if (expect.has("lease_id")) {
            if (expect.get("lease_id").isNull()) {
                assertNull(entry, "get_lease should have found no lease");
            } else {
                assertNotNull(entry, "get_lease should have found a lease");
                assertEquals(expect.get("lease_id").asText(), entry.getLeaseId(), "get_lease lease_id");
            }
        }
        if (expect.has("granted_amount")) {
            assertNotNull(entry, "get_lease should have found a lease");
            assertEquals(expect.get("granted_amount").asDouble(), entry.getGrantedAmount(), "get_lease granted");
        }
        if (expect.has("local_remaining_credits")) {
            assertNotNull(entry, "get_lease should have found a lease");
            assertEquals(
                    expect.get("local_remaining_credits").asDouble(),
                    entry.getLocalRemainingCredits(),
                    "get_lease local_remaining_credits");
        }
    }

    private static void opAddReservation(Harness h, JsonNode op) {
        Map<String, String> company = new HashMap<>();
        company.put("id", text(op, "company_id"));
        h.backend.reservations.add(new Reservation(
                text(op, "id"),
                text(op, "lease_id"),
                CreditLeaseMode.CLIENT,
                text(op, "company_id"),
                text(op, "credit_type_id"),
                text(op, "event_subtype"),
                op.get("quantity_reserved").asDouble(),
                op.get("credits_reserved").asDouble(),
                op.get("consumption_rate").asDouble(),
                h.backend.clock.at(op.get("expires_at_ms").asDouble()),
                company,
                null));
    }

    private static void opConsumeReservation(Harness h, JsonNode op, JsonNode expect) {
        String id = h.resolveReservationId(op);
        double credits = op.get("credits").asDouble();
        if (op.has("crash_before_refund") && op.get("crash_before_refund").asBoolean()) {
            h.backend.crash.arm();
            assertThrows(
                    CrashingRefundLeaseStore.SimulatedCrash.class,
                    () -> h.backend.reservations.consume(id, credits),
                    "consume_reservation should have crashed before the refund");
            assertTrue(expect.get("throws").asBoolean(), "consume_reservation throws");
            return;
        }
        Double consumed = h.backend.reservations.consume(id, credits);
        if (expect.has("consumed")) {
            if (expect.get("consumed").isNull()) {
                assertNull(consumed, "consume_reservation should have claimed nothing");
            } else {
                assertNotNull(consumed, "consume_reservation should have claimed the hold");
                assertEquals(expect.get("consumed").asDouble(), consumed, "consume_reservation consumed");
            }
        }
    }

    private static void opCheck(Harness h, JsonNode op, JsonNode expect) {
        String flagKey = op.has("flag_key") ? op.get("flag_key").asText() : "flag";
        JsonNode companySpec = op.get("company");
        String companyId = companySpec != null && companySpec.has("id")
                ? companySpec.get("id").asText()
                : "co_1";
        Map<String, Double> balances = new LinkedHashMap<>();
        if (companySpec != null && companySpec.has("credit_balances")) {
            Iterator<Map.Entry<String, JsonNode>> fields =
                    companySpec.get("credit_balances").fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                balances.put(field.getKey(), field.getValue().asDouble());
            }
        }
        List<ScriptedCheckDataStream.Result> results = new ArrayList<>();
        if (op.has("engine")) {
            for (JsonNode scripted : op.get("engine")) {
                results.add(new ScriptedCheckDataStream.Result(
                        scripted.get("value").asBoolean(),
                        scripted.has("reason") ? scripted.get("reason").asText() : null,
                        entitlementFrom(scripted.get("entitlement"))));
            }
        }
        if (op.has("server")) {
            JsonNode server = op.get("server");
            if (server.has("acquire")) {
                h.wire.queueAcquire(acquireScript(h, server.get("acquire")));
            }
            if (server.has("extend")) {
                h.wire.queueExtend(extendScript(h, server.get("extend")));
            }
        }

        ScriptedCheckDataStream dataStream =
                new ScriptedCheckDataStream(flagKey, ScriptedCheckDataStream.company(companyId, balances), results);
        boolean[] fellBack = {false};
        Callable<CheckResult> fallback = () -> {
            fellBack[0] = true;
            return new CheckResult(true, true, "fallback", flagKey, null, null, null, null);
        };

        CreditCheck flow = new CreditCheck(
                dataStream,
                h.backend.leases,
                h.backend.reservations,
                h.manager(),
                null,
                h.backend.clock.clock(),
                null,
                null);
        CheckResult result = flow.check(
                new CheckRequest(
                        flagKey,
                        Collections.singletonMap("id", companyId),
                        null,
                        op.has("usage") ? op.get("usage").asDouble() : 0,
                        op.has("event_subtype") ? op.get("event_subtype").asText() : null,
                        "fail-open".equals(text(op, "on_acquire_failure"))),
                fallback);
        h.manager().drain(DRAIN_TIMEOUT);

        if (expect.has("allowed")) {
            assertEquals(expect.get("allowed").asBoolean(), result.isAllowed(), "allowed");
        }
        if (expect.has("reason")) {
            assertEquals(expect.get("reason").asText(), result.getReason(), "reason");
        }
        if (expect.has("err")) {
            assertEquals(expect.get("err").asText(), result.getErr(), "err");
        }
        if (expect.has("has_reservation")) {
            assertEquals(expect.get("has_reservation").asBoolean(), result.getReservation() != null, "has_reservation");
        }
        if (expect.has("fallback_called")) {
            assertEquals(expect.get("fallback_called").asBoolean(), fellBack[0], "fallback_called");
        }
        assertReservation(expect.get("reservation"), result.getReservation());
        assertEngineCalls(expect.get("engine_calls"), dataStream.calls, creditIdFor(op, balances));
        if (expect.has("wire_extends")) {
            assertEquals(expect.get("wire_extends").asInt(), h.wire.extendCalls.size(), "wire_extends");
        }
        if (expect.has("last_extend_additional_amount")) {
            assertFalse(h.wire.extendCalls.isEmpty(), "no extend was made");
            assertEquals(
                    expect.get("last_extend_additional_amount").asDouble(),
                    h.wire.extendCalls.get(h.wire.extendCalls.size() - 1).additionalAmount,
                    "last_extend_additional_amount");
        }
        if (op.has("save_reservation_as") && result.getReservation() != null) {
            h.handles.put(op.get("save_reservation_as").asText(), result.getReservation());
        }
    }

    private static void opTrack(Harness h, JsonNode op, JsonNode expect) {
        Reservation reservation = h.handles.get(op.get("handle").asText());
        assertNotNull(
                reservation, "unknown reservation handle: " + op.get("handle").asText());
        ReservationSettlement.SettleOutcome outcome = ReservationSettlement.settle(
                h.backend.reservations, reservation, op.get("actual_quantity").asDouble());

        if (expect.has("settled_locally")) {
            assertEquals(expect.get("settled_locally").asBoolean(), outcome.isSettledLocally(), "settled_locally");
        }
        JsonNode want = expect.get("track");
        if (want == null) {
            return;
        }
        EventBodyTrack track = outcome.getTrack();
        if (want.has("event")) {
            assertEquals(want.get("event").asText(), track.getEvent(), "track event");
        }
        if (want.has("quantity")) {
            // The vector states the caller's actual usage, which can be fractional. This SDK's
            // event quantity is an integer on the wire, so a partial unit bills as the whole one
            // the hold and the debit were already sized for.
            long quantity = (long) Math.ceil(want.get("quantity").asDouble());
            assertEquals(quantity, track.getQuantity().orElse(null), "track quantity");
        }
        if (want.has("lease_id")) {
            assertEquals(want.get("lease_id").asText(), track.getLeaseId().orElse(null), "track lease_id");
        }
        if (want.has("reservation_id")) {
            assertEquals(
                    want.get("reservation_id").asText(),
                    track.getReservationId().orElse(null),
                    "track reservation_id");
        }
    }

    private static void assertReservation(JsonNode want, Reservation reservation) {
        if (want == null) {
            return;
        }
        assertNotNull(reservation, "expected a reservation");
        if (want.has("lease_id")) {
            assertEquals(want.get("lease_id").asText(), reservation.getLeaseId(), "reservation lease_id");
        }
        if (want.has("credit_type_id")) {
            assertEquals(
                    want.get("credit_type_id").asText(), reservation.getCreditTypeId(), "reservation credit_type_id");
        }
        if (want.has("event_subtype")) {
            assertEquals(
                    want.get("event_subtype").asText(), reservation.getEventSubtype(), "reservation event_subtype");
        }
        if (want.has("quantity_reserved")) {
            assertEquals(
                    want.get("quantity_reserved").asDouble(),
                    reservation.getQuantityReserved(),
                    "reservation quantity_reserved");
        }
        if (want.has("credits_reserved")) {
            assertEquals(
                    want.get("credits_reserved").asDouble(),
                    reservation.getCreditsReserved(),
                    "reservation credits_reserved");
        }
        if (want.has("consumption_rate")) {
            assertEquals(
                    want.get("consumption_rate").asDouble(),
                    reservation.getConsumptionRate(),
                    "reservation consumption_rate");
        }
    }

    private static void assertEngineCalls(
            JsonNode want, List<ScriptedCheckDataStream.EngineCall> calls, String creditId) {
        if (want == null) {
            return;
        }
        assertEquals(want.size(), calls.size(), "engine call count");
        for (int i = 0; i < want.size(); i++) {
            JsonNode expected = want.get(i);
            ScriptedCheckDataStream.EngineCall got = calls.get(i);
            if (expected.has("credit_balance")) {
                assertNotNull(creditId, "engine_calls needs a credit id to assert a balance against");
                assertNotNull(got.creditBalances, "the engine was given no company");
                assertEquals(
                        expectedCreditBalance(expected.get("credit_balance")),
                        got.creditBalances.get(creditId),
                        "engine call " + i + " credit_balance");
            }
            if (expected.has("credit_cost")) {
                assertNotNull(got.preflight, "engine call " + i + " carried no preflight");
                assertNotNull(got.preflight.getCreditCost(), "engine call " + i + " carried no credit cost");
                assertEquals(
                        expected.get("credit_cost").asDouble(),
                        got.preflight.getCreditCost().get(creditId),
                        "engine call " + i + " credit_cost");
            }
            if (expected.has("event_usage")) {
                assertNotNull(got.preflight, "engine call " + i + " carried no preflight");
                PreflightOptions.EventUsage eventUsage = got.preflight.getEventUsage();
                assertNotNull(eventUsage, "engine call " + i + " carried no event usage");
                assertEquals(
                        expected.get("event_usage").get("event_subtype").asText(),
                        eventUsage.getEventSubtype(),
                        "engine call " + i + " event_subtype");
                assertEquals(
                        expected.get("event_usage").get("quantity").asLong(),
                        eventUsage.getQuantity(),
                        "engine call " + i + " event quantity");
            }
            if (expected.has("usage")) {
                assertNotNull(got.preflight, "engine call " + i + " carried no preflight");
                assertEquals(
                        (Long) expected.get("usage").asLong(), got.preflight.getUsage(), "engine call " + i + " usage");
            }
        }
    }

    /** A balance expectation is a number, or the name of the fail-open substitution. */
    private static Double expectedCreditBalance(JsonNode raw) {
        if (raw.isTextual() && "max_safe_integer".equals(raw.asText())) {
            return CreditLeaseDefaults.FAIL_OPEN_BALANCE;
        }
        return raw.asDouble();
    }

    /**
     * The credit a vector's balance and cost expectations are about: the one the scripted
     * entitlement meters, or the company's only balance when no entitlement names one.
     */
    private static String creditIdFor(JsonNode op, Map<String, Double> balances) {
        if (op.has("engine")) {
            for (JsonNode scripted : op.get("engine")) {
                JsonNode entitlement = scripted.get("entitlement");
                if (entitlement != null && entitlement.has("credit_id")) {
                    return entitlement.get("credit_id").asText();
                }
            }
        }
        for (String creditId : balances.keySet()) {
            return creditId;
        }
        return null;
    }

    private static RulesengineFeatureEntitlement entitlementFrom(JsonNode spec) {
        if (spec == null) {
            return null;
        }
        return ScriptedCheckDataStream.entitlement(
                spec.get("value_type").asText(),
                spec.has("credit_id") ? spec.get("credit_id").asText() : null,
                spec.has("consumption_rate") ? spec.get("consumption_rate").asDouble() : null,
                spec.has("event_subtype") ? spec.get("event_subtype").asText() : null);
    }

    private static void opAcquireIfNeeded(Harness h, JsonNode op, JsonNode expect) {
        if (op.has("server")) {
            h.wire.queueAcquire(acquireScript(h, op.get("server")));
        }
        if (op.has("install_during_wire")) {
            JsonNode install = op.get("install_during_wire");
            h.wire.duringAcquire = () -> h.backend.leases.replace(new LeaseGrant(
                    install.get("lease_id").asText(),
                    install.get("company_id").asText(),
                    install.get("credit_type_id").asText(),
                    install.get("granted_amount").asDouble(),
                    h.backend.clock.at(install.get("expires_at_ms").asDouble())));
        }
        LeaseState entry = h.manager().acquireIfNeeded(text(op, "company_id"), text(op, "credit_type_id"));
        h.manager().drain(DRAIN_TIMEOUT);

        if (expect.has("lease_id")) {
            if (expect.get("lease_id").isNull()) {
                assertNull(entry, "acquire_if_needed should have found no lease");
            } else {
                assertNotNull(entry, "acquire_if_needed should have returned a lease");
                assertEquals(expect.get("lease_id").asText(), entry.getLeaseId(), "acquire_if_needed lease_id");
            }
        }
        if (expect.has("wire_acquires")) {
            assertEquals(expect.get("wire_acquires").asInt(), h.wire.acquireCalls.size(), "wire_acquires");
        }
        if (expect.has("last_acquire_requested_amount")) {
            assertFalse(h.wire.acquireCalls.isEmpty(), "no acquire was made");
            assertEquals(
                    expect.get("last_acquire_requested_amount").asDouble(),
                    h.wire.acquireCalls.get(h.wire.acquireCalls.size() - 1).requestedAmount,
                    "last_acquire_requested_amount");
        }
        assertReleasedLeaseIds(h, expect);
    }

    private static void opMaybeExtend(Harness h, JsonNode op, JsonNode expect) {
        if (op.has("server")) {
            h.wire.queueExtend(extendScript(h, op.get("server")));
        }
        Double required =
                op.has("required_credits") ? op.get("required_credits").asDouble() : null;
        h.manager().maybeExtend(text(op, "company_id"), text(op, "credit_type_id"), required);
        h.manager().drain(DRAIN_TIMEOUT);

        if (expect.has("wire_extends")) {
            assertEquals(expect.get("wire_extends").asInt(), h.wire.extendCalls.size(), "wire_extends");
        }
        if (expect.has("last_extend_additional_amount")) {
            assertFalse(h.wire.extendCalls.isEmpty(), "no extend was made");
            assertEquals(
                    expect.get("last_extend_additional_amount").asDouble(),
                    h.wire.extendCalls.get(h.wire.extendCalls.size() - 1).additionalAmount,
                    "last_extend_additional_amount");
        }
        if (expect.has("last_extend_lease_id")) {
            assertFalse(h.wire.extendCalls.isEmpty(), "no extend was made");
            assertEquals(
                    expect.get("last_extend_lease_id").asText(),
                    h.wire.extendCalls.get(h.wire.extendCalls.size() - 1).leaseId,
                    "last_extend_lease_id");
        }
    }

    private static void opReleaseAllLocalLeases(Harness h, JsonNode expect) {
        h.manager().releaseAllLocalLeases();
        assertReleasedLeaseIds(h, expect);
        if (expect.has("remaining_slots")) {
            assertTrue(h.backend.leases instanceof LeaseLister, "remaining_slots needs an enumerable store");
            assertEquals(
                    expect.get("remaining_slots").asInt(),
                    ((LeaseLister) h.backend.leases).list().size(),
                    "remaining_slots");
        }
    }

    private static void assertReleasedLeaseIds(Harness h, JsonNode expect) {
        if (!expect.has("released_lease_ids")) {
            return;
        }
        List<String> released = new ArrayList<>();
        for (JsonNode id : expect.get("released_lease_ids")) {
            released.add(id.asText());
        }
        assertEquals(released, h.wire.releasedLeaseIds, "released_lease_ids");
    }

    private static ScriptedWireClient.Script acquireScript(Harness h, JsonNode server) {
        if (server.has("error")) {
            return ScriptedWireClient.Script.error(server.get("error").asText());
        }
        JsonNode lease = server.get("lease");
        return ScriptedWireClient.Script.lease(
                lease.get("lease_id").asText(),
                lease.get("granted_amount").asDouble(),
                h.backend.clock.at(lease.get("expires_at_ms").asDouble()));
    }

    private static ScriptedWireClient.Script extendScript(Harness h, JsonNode server) {
        if (server.has("error")) {
            return ScriptedWireClient.Script.error(server.get("error").asText());
        }
        JsonNode lease = server.get("lease");
        return ScriptedWireClient.Script.lease(
                lease.has("lease_id") ? lease.get("lease_id").asText() : null,
                lease.get("granted_total").asDouble(),
                h.backend.clock.at(lease.get("expires_at_ms").asDouble()));
    }

    private static String text(JsonNode op, String field) {
        JsonNode value = op.get(field);
        return value == null || value.isNull() ? null : value.asText();
    }

    /** One vector's stores, clock and reservation handles. */
    static final class Harness {

        final Backend backend;
        final JsonNode given;
        final Map<String, Reservation> handles = new HashMap<>();
        final ScriptedWireClient wire = new ScriptedWireClient();
        private CreditLeaseManager manager;

        Harness(Backend backend, JsonNode given) {
            this.backend = backend;
            this.given = given == null ? MAPPER.createObjectNode() : given;
        }

        /** The manager under the vector's config, built on first use. */
        CreditLeaseManager manager() {
            if (manager == null) {
                JsonNode config = config();
                CreditLeaseConfig.Builder builder = CreditLeaseConfig.builder();
                if (config.has("lease_duration_ms")) {
                    builder.defaultLeaseDuration(
                            Duration.ofMillis(config.get("lease_duration_ms").asLong()));
                }
                if (config.has("reservation_ttl_ms")) {
                    builder.defaultReservationTtl(
                            Duration.ofMillis(config.get("reservation_ttl_ms").asLong()));
                }
                if (config.has("lease_size")) {
                    builder.defaultLeaseSize(config.get("lease_size").asDouble());
                }
                if (config.has("low_water_mark")) {
                    builder.lowWaterMark(config.get("low_water_mark").asDouble());
                }
                manager = new CreditLeaseManager(
                        wire, backend.leases, backend.reservations, builder.build(), null, backend.clock.clock());
            }
            return manager;
        }

        void installGivenLeases() {
            JsonNode leases = given.get("leases");
            if (leases == null) {
                return;
            }
            for (JsonNode lease : leases) {
                boolean wrote = backend.leases.replace(new LeaseGrant(
                        lease.get("lease_id").asText(),
                        lease.get("company_id").asText(),
                        lease.get("credit_type_id").asText(),
                        lease.get("granted_amount").asDouble(),
                        backend.clock.at(lease.get("expires_at_ms").asDouble())));
                assertTrue(wrote, "given.leases must install cleanly");
            }
        }

        JsonNode config() {
            JsonNode config = given.get("config");
            return config == null ? MAPPER.createObjectNode() : config;
        }

        /** Maps a vector's handle back to the id the check that issued it returned. */
        String resolveReservationId(JsonNode op) {
            if (op.has("handle")) {
                Reservation reservation = handles.get(op.get("handle").asText());
                assertNotNull(
                        reservation,
                        "unknown reservation handle: " + op.get("handle").asText());
                return reservation.getId();
            }
            assertTrue(op.has("id"), "op " + op.get("op").asText() + " needs an id or handle");
            return op.get("id").asText();
        }

        void close() {
            handles.clear();
            if (manager != null) {
                manager.close();
            }
        }
    }
}
