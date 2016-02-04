package com.jakespringer.lostexhaust.user;

import java.util.List;

public interface UserContext {
    public String getId();
    public String getFirstname();
    public String getLastname();
    public List<HouseholdContext> getHouseholds();
    public String getAffiliation();
    public String getGradeLevel();
    public String getClassYear();
    public List<Contact> getContactInfo();
    public List<Relationship> getRelationships();
    public byte[] getProfilePicture();
    
    public default void invalidate() {
        // do nothing by default
    }
}
