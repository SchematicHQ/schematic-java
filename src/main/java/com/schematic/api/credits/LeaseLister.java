package com.schematic.api.credits;

import java.util.List;

/**
 * Implemented only by a per-process store, whose leases are exclusively this process's, so
 * releasing them on close is safe. A shared backend must never enumerate and release: sibling
 * processes still draw on those leases.
 */
public interface LeaseLister {

    /** A snapshot of every slot this process holds. */
    List<LeaseState> list();
}
