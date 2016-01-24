package com.jakespringer.lostexhaust.test;

import java.util.List;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.user.UserContext;

public class StaticUserContext implements UserContext {

    private String id;
    private String firstname;
    private String lastname;
    private List<HouseholdContext> households;
    private String affiliation;
    private String gradeLevel;
    private String classYear;
    private List<Contact> contactInfo;
    private List<Relationship> relationships;
    private byte[] profilePicture;
    
    public StaticUserContext(String _id, String _firstname, String _lastname,
            List<HouseholdContext> _households, String _affiliation,
            String _gradeLevel, String _classYear, List<Contact> _contactInfo,
            List<Relationship> _relationships, byte[] _profilePicture) {
        id = _id;
        firstname = _firstname;
        lastname = _lastname;
        households = _households;
        affiliation = _affiliation;
        gradeLevel = _gradeLevel;
        classYear = _classYear;
        contactInfo = _contactInfo;
        relationships = _relationships;
        profilePicture = _profilePicture; 
    }
            
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public List<HouseholdContext> getHouseholds() {
        return households;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    @Override
    public String getGradeLevel() {
        return gradeLevel;
    }

    @Override
    public String getClassYear() {
        return classYear;
    }

    @Override
    public List<Contact> getContactInfo() {
        return contactInfo;
    }

    @Override
    public List<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture;
    }

}
