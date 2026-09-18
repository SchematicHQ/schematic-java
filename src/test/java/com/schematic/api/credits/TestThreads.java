package com.schematic.api.credits;

import java.util.concurrent.TimeUnit;

/** Thread-state helpers shared by the lease tests. */
final class TestThreads {

    private TestThreads() {}

    /**
     * Waits, boundedly, for a thread to block. Its state is the signal a test has that the thread
     * reached the wait under test, which is what lets these tests orchestrate a race without a
     * sleep long enough to be slow and short enough to be flaky.
     */
    static boolean parked(Thread thread) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(10);
        while (System.nanoTime() - deadline < 0) {
            Thread.State state = thread.getState();
            if (state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) {
                return true;
            }
            if (state == Thread.State.TERMINATED) {
                return false;
            }
            Thread.sleep(1);
        }
        return false;
    }
}
