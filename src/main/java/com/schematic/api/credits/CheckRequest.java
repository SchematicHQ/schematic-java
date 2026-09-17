package com.schematic.api.credits;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** One caller's ask, as the credit-gated flows take it. */
public final class CheckRequest {

    private final String flagKey;
    private final Map<String, String> company;
    private final Map<String, String> user;
    private final double usage;
    private final String eventSubtype;
    private final boolean failOpen;

    public CheckRequest(
            String flagKey,
            Map<String, String> company,
            Map<String, String> user,
            double usage,
            String eventSubtype,
            boolean failOpen) {
        this.flagKey = flagKey;
        this.company = copy(company);
        this.user = copy(user);
        this.usage = usage;
        this.eventSubtype = eventSubtype;
        this.failOpen = failOpen;
    }

    private static Map<String, String> copy(Map<String, String> keys) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(keys));
    }

    public String getFlagKey() {
        return flagKey;
    }

    /** The caller's evaluation keys, threaded onto the hold so the settling event attributes usage the same way. */
    public Map<String, String> getCompany() {
        return company;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public double getUsage() {
        return usage;
    }

    /** Names the event the usage applies to. Empty defers to the entitlement's own subtype. */
    public String getEventSubtype() {
        return eventSubtype;
    }

    /** Errs on the side of assuming the credits are there when the flow cannot gate. */
    public boolean isFailOpen() {
        return failOpen;
    }
}
