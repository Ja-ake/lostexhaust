package com.jakespringer.lostexhaust.user;

import java.util.List;
import com.jakespringer.lostexhaust.data.CatlinUserContext;

public class UserContextFactory {
    public static UserContext get(String id) {
        List<UserContext> users = ContextCache.getUsers();
        for (UserContext user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }

        return new CatlinUserContext(id);
    }
}
