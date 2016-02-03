package com.jakespringer.lostexhaust.user;

public class Relationship {
    public final UserContext user;
    public final String type;
    public final String firstname;
    public final String lastname;
    
    public Relationship(UserContext _user, String _type) {
        user = _user;
        type = _type;
        firstname = null;
        lastname = null;
    }
    
    public Relationship(UserContext _user, String _type, String _firstname, String _lastname) {
        user = _user;
        type = _type;
        firstname = _firstname;
        lastname = _lastname;
    }
}
