package com.jakespringer.lostexhaust.test;

import com.jakespringer.lostexhaust.user.SessionService;
import com.jakespringer.lostexhaust.user.UserSession;
import com.jakespringer.lostexhaust.util.Timestamp;

public class NicholasTestSessionService implements SessionService {
    private UserSession testSession = new UserSession(Tests.nuc, "127.0.0.1", new Timestamp(0));

    @Override
    public UserSession getSession(String cookie) {
        return testSession;
    }
}
