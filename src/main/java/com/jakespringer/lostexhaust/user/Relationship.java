package com.jakespringer.lostexhaust.user;

public class Relationship {
    public final UserContext user;
    public final String type;
    
    public Relationship(UserContext _user, String _type) {
        user = _user;
        type = _type;
    }
}
