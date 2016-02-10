package com.jakespringer.lostexhaust.util;

public class Timer {
    private final long startTime;
    private final String name;

    private Timer(String _name, long _startTime) {
        name = _name;
        startTime = _startTime;
    }

    public static Timer start(String name) {
        return new Timer(name, System.nanoTime() / 1000000L);
    }

    public void stop() {
        System.out.println(name + " took " + ((System.nanoTime() / 1000000L) - startTime) + "ms to complete.");
    }
}
