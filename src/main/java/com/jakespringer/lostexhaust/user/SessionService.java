package com.jakespringer.lostexhaust.user;

public interface SessionService {
    public UserSession getSession(String cookie);
}
