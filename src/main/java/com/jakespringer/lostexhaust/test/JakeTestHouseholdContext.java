package com.jakespringer.lostexhaust.test;

import java.util.List;
import com.google.common.collect.Lists;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.util.Coordinates;

public class JakeTestHouseholdContext implements HouseholdContext {
    @Override
    public String getPlaceId() {
        return "ChIJXXo8PucLlVQRbhTDDUyanfM";
    }

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates(0, 0);
    }

    @Override
    public String getAddress() {
        return "3065 SW 70th Ave, Portland, OR";
    }

    @Override
    public List<UserContext> getResidents() {
        return Lists.newArrayList(Tests.juc, Tests.nuc);
    }
}
