package com.jakespringer.lostexhaust.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.jakespringer.lostexhaust.error.SessionExpiredException;
import com.jakespringer.lostexhaust.user.ContextCache;
import com.jakespringer.lostexhaust.user.SessionService;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.user.UserSession;
import com.jakespringer.lostexhaust.util.Timestamp;

public class CatlinSessionService implements SessionService {

    private List<UserSession> sessions = new ArrayList<>();
    
    @Override
    public UserSession getSession(String cookie, String ip) {
        CatlinCrypto.Key key = CatlinCrypto.getMessageFromString(cookie);
        if (key == null
        		//|| !key.ip.equals(ip)
        		) {
            return null;
        } else {
            // prune sessions
            sessions.removeIf(x -> !x.isStillValid());

            // first, check the active sessions
            Optional<UserSession> possibleSession = sessions.stream().filter(x -> {
                try {
                    return x.isStillValid() 
                        //&& x.getIp().equals(ip)
                        && x.getContext().getId().equals(key.id);
                } catch (SessionExpiredException e) {
                    // should never happen
                    return false;
                }
            }).findFirst();
                        
            if (possibleSession.isPresent()) return possibleSession.get();
            
            // second, check the user cache
            System.out.println(ContextCache.getUsers().size());
            Optional<UserContext> possibleContext = ContextCache.getUsers().stream().filter(x -> 
                    x.getId().equals(key.id)).findFirst();
            
            if (possibleContext.isPresent()) {
                UserSession newSession = new UserSession(possibleContext.get(), ip, Timestamp.currentTime());
                sessions.add(newSession);
                return newSession;
            }
            
            // third, check sql
            // TODO
            
            // fourth, give up
            return null;
        }
    }
}
