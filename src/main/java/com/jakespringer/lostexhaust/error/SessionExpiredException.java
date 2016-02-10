package com.jakespringer.lostexhaust.error;

public class SessionExpiredException extends Exception {
    public SessionExpiredException() {
        super();
    }

    public SessionExpiredException(String thing) {
        super(thing);
    }
}
