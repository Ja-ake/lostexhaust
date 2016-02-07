package com.jakespringer.lostexhaust.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.HouseholdContextFactory;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.user.UserContext;

public class CatlinUserContext implements UserContext {

    private final String userId;
    private CatlinPerson personDetails;
    private byte[] profilePicture;
    private List<HouseholdContext> households;
    
    public CatlinUserContext(String _userId) {
        userId = _userId;
    }
    
    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String getFirstname() {
    	ifNeededUpdateDetails();
        return personDetails.firstname;
    }

    @Override
    public String getLastname() {
    	ifNeededUpdateDetails();
        return personDetails.lastname;
    }

    @Override
    public List<HouseholdContext> getHouseholds() {
    	ifNeededUpdateHouseholds();
        return Collections.unmodifiableList(households);
    }

    @Override
    public String getAffiliation() {
    	ifNeededUpdateDetails();
        return personDetails.affiliation;
    }

    @Override
    public String getGradeLevel() {
    	ifNeededUpdateDetails();
        return personDetails.gradeLevel;
    }

    @Override
    public String getClassYear() {
    	ifNeededUpdateDetails();
        return personDetails.classYear;
    }

    @Override
    public List<Contact> getContactInfo() {
    	ifNeededUpdateDetails();
        return personDetails.contact;
    }

    @Override
    public List<Relationship> getRelationships() {
    	ifNeededUpdateDetails();
    	return personDetails.relationships;
    }

    @Override
    public byte[] getProfilePicture() {
    	ifNeededUpdatePicture();
        return profilePicture;
    }

    @Override
    public void invalidate() {
    	personDetails = null;
    	profilePicture = null;
    	households = null;
    }
    
    private void ifNeededUpdateHouseholds() {
        if (households == null) {
            try {
                households = CatlinSql.inst.getHouseholdsFromUser(userId)
                        .stream()
                        .map(HouseholdContextFactory::get)
                        .collect(Collectors.toList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private void ifNeededUpdateDetails() {
    	if (personDetails == null) {
    		try {
				personDetails = CatlinApi.getPerson(userId);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
    	}
    }
    
    private void ifNeededUpdatePicture() {
    	if (profilePicture == null) {
    		try {
    			profilePicture = CatlinApi.getProfilePicture(userId);
    		} catch (IOException e) {
    			throw new RuntimeException(e);
    		}
    	}
    }
}
