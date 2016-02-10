package com.jakespringer.lostexhaust.util;

public class Timestamp {
    public final long ticks;

    public Timestamp(long t) {
        ticks = t;
    }

    /**
     * Calculates the difference between two Timestamps by subtracting this
     * Timestamp from the parameter given.
     */
    public long delta(Timestamp newer) {
        return newer.ticks - ticks;
    }

    /**
     * Checks if a Timestamp is still valid. Calculated with (this.delta(newer)
     * > epsilon)
     */
    public boolean validate(Timestamp newer, long epsilon) {
        return delta(newer) < epsilon;
    }

    /**
     * Returns a Timestamp containing the current time.
     */
    public static Timestamp currentTime() {
        return new Timestamp(System.currentTimeMillis() / 1000L);
    }
}
