package com.jakespringer.lostexhaust.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContextCache {
    private static final List<UserContext> users = new ArrayList<>();
    private static final List<HouseholdContext> households = new ArrayList<>();
    
    public static List<UserContext> getUsers() {
        return Collections.unmodifiableList(users);
    }
    
    public static List<HouseholdContext> getHouseholds() {
        return Collections.unmodifiableList(households);
    }
    
    public static void addUser(UserContext c) {
    	System.out.println("added a user named " + c.getFirstname());
        if (c == null) throw new NullPointerException();
        users.removeIf(e -> e.getId().equals(c.getId()));
        users.add(c);
    }
    
    public static void addHousehold(HouseholdContext c) {
        if (c == null) throw new NullPointerException();
        households.removeIf(e -> e.getPlaceId().equals(c.getPlaceId()));
        households.add(c);
    }
    
    public static void removeUser(String id) {
        users.removeIf(e -> e.getId().equals(id));
    }
    
    public static void removeHousehold(String placeid) {
        households.removeIf(e -> e.getPlaceId().equals(placeid));
    }
}
