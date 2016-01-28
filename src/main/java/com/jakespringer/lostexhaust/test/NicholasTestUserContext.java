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

public class NicholasTestUserContext implements UserContext {
    
    @Override
    public String getId() {
        return "ST19029";
    }

    @Override
    public List<HouseholdContext> getHouseholds() {
        return Lists.newArrayList(Tests.nhc);
    }

    @Override
    public String getAffiliation() {
        return "Student";
    }

    @Override
    public String getGradeLevel() {
        return "9";
    }

    @Override
    public String getClassYear() {
        return "2019";
    }

    @Override
    public List<Contact> getContactInfo() {
        return Lists.newArrayList(new Contact("Primary Email", "springern@catlin.edu"),
                                  new Contact("Cell Phone", "(222) 206-1890"));
    }

    @Override
    public byte[] getProfilePicture() {
        try {
            return Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR + "/src/main/webapp/public/img/ST19029-large.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public String getFirstname() {
        return "Nicholas";
    }

    @Override
    public String getLastname() {
        return "Springer";
    }

    @Override
    public List<Relationship> getRelationships() {
        return Lists.newArrayList(new Relationship(new JakeTestUserContext(), "Sibling"));
    }

}
