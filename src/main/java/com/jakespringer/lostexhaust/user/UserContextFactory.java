package com.jakespringer.lostexhaust.user;

import com.jakespringer.lostexhaust.test.JakeTestUserContext;
import com.jakespringer.lostexhaust.test.NicholasTestUserContext;

public class UserContextFactory {
    public static UserContext get(String id) {
        if (id == null) return null;
        
        if (id.equals("ST16421")) {
            return new JakeTestUserContext();
        } else if (id.equals("ST19029")) {
            return new NicholasTestUserContext();
        } else return null;
    }
}
