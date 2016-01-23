package com.jakespringer.lostexhaust.user;

import com.jakespringer.lostexhaust.LeService;
import com.jakespringer.lostexhaust.error.SessionExpiredException;
import com.jakespringer.lostexhaust.util.Timestamp;

public class UserSession {
    private final UserContext context;
    private final String ip;
    private final Timestamp created;
    
    public UserSession(UserContext _context, String _ip, Timestamp _created) {
        context = _context;
        ip = _ip;
        created = _created;
    }
    
    public UserContext getContext() throws SessionExpiredException {
        if (!isStillValid()) throw new SessionExpiredException();
        else return context;
    }
    
    public Timestamp getCreationTimestamp() {
        return created;
    }
    
    public String getIp() {
        return ip;
    }
    
    public boolean isStillValid() {
        return created.validate(Timestamp.currentTime(), 
                LeService.getValidityDuration());
    }
}
