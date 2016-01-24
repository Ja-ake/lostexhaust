package com.jakespringer.lostexhaust.user;

import java.util.List;

public class HouseholdContextFactory {
    public static HouseholdContext get(String placeid) {
        List<HouseholdContext> households = ContextCache.getHouseholds();
        for (HouseholdContext household : households) {
            if (household.getPlaceId().equals(placeid)) {
                return household;
            }
        }
        
        return null;
    }
}
