package com.jakespringer.lostexhaust.user;

import java.util.List;
import com.jakespringer.lostexhaust.data.CatlinHouseholdContext;

public class HouseholdContextFactory {
    public static HouseholdContext get(String id) {
        List<HouseholdContext> households = ContextCache.getHouseholds();
        for (HouseholdContext household : households) {
            if (household.getId().equals(id)) {
                return household;
            }
        }

        return new CatlinHouseholdContext(id);
    }
}
