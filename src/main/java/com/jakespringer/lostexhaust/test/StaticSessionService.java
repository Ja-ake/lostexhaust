package com.jakespringer.lostexhaust.test;

import com.jakespringer.lostexhaust.user.SessionService;
import com.jakespringer.lostexhaust.user.UserSession;
import com.jakespringer.lostexhaust.util.Timestamp;

public class StaticSessionService implements SessionService {

    @Override
    public UserSession getSession(String cookie) {
        switch (cookie) {
        case "brady": return new UserSession(Tests.ubrady, "127.0.0.1", Timestamp.currentTime());
        
        default: return null;
        }
    }
}
