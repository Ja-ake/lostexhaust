package com.jakespringer.lostexhaust.data;

import java.util.List;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.user.UserContext;

public class CatlinUserContext implements UserContext {

    private final String userId;
    
    public CatlinUserContext(String _userId) {
        userId = _userId;
    }
    
    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String getFirstname() {
        return null;
    }

    @Override
    public String getLastname() {
        return null;
    }

    @Override
    public List<HouseholdContext> getHouseholds() {
        return null;
    }

    @Override
    public String getAffiliation() {
        return null;
    }

    @Override
    public String getGradeLevel() {
        return null;
    }

    @Override
    public String getClassYear() {
        return null;
    }

    @Override
    public List<Contact> getContactInfo() {
        return null;
    }

    @Override
    public List<Relationship> getRelationships() {
        return null;
    }

    @Override
    public byte[] getProfilePicture() {
        return null;
    }

}
