package com.jakespringer.lostexhaust.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.google.common.collect.Lists;
import com.jakespringer.lostexhaust.LeWebserver;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.user.UserContext;

public class JakeTestUserContext implements UserContext {
    
    @Override
    public String getId() {
        return "ST16421";
    }

    @Override
    public List<HouseholdContext> getHouseholds() {
        return Lists.newArrayList(new JakeTestHouseholdContext());
    }

    @Override
    public String getAffiliation() {
        return "Student";
    }

    @Override
    public String getGradeLevel() {
        return "11";
    }

    @Override
    public String getClassYear() {
        return "2017";
    }

    @Override
    public List<Contact> getContactInfo() {
        return Lists.newArrayList(new Contact("Primary Email", "springerj@catlin.edu"),
                                  new Contact("Cell Phone", "(971) 200-1498"));
    }

    @Override
    public byte[] getProfilePicture() {
        try {
            return Files.readAllBytes(Paths.get(LeWebserver.REQ_APP_DIR + "/src/main/webapp/public/img/ST16421-large.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public String getFirstname() {
        return "Jake";
    }

    @Override
    public String getLastname() {
        return "Springer";
    }

    @Override
    public List<Relationship> getRelationships() {
        return Lists.newArrayList(new Relationship(new NicholasTestUserContext(), "Sibling"));
    }

}
