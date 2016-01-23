package com.jakespringer.lostexhaust.test;

import com.jakespringer.lostexhaust.user.ContextCache;

public class Tests {
    public static final JakeTestHouseholdContext jhc = new JakeTestHouseholdContext();
    public static final NicholasTestHouseholdContext nhc = new NicholasTestHouseholdContext();
    public static final JakeTestSessionService jss = new JakeTestSessionService();
    public static final NicholasTestSessionService nss = new NicholasTestSessionService();
    public static final JakeTestUserContext juc = new JakeTestUserContext();
    public static final NicholasTestUserContext nuc = new NicholasTestUserContext();
    
    static {
        ContextCache.addHousehold(jhc);
        ContextCache.addHousehold(nhc);
        ContextCache.addUser(juc);
        ContextCache.addUser(nuc);
    }
}
