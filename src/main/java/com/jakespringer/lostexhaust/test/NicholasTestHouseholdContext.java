package com.jakespringer.lostexhaust.test;

import java.util.List;
import com.google.common.collect.Lists;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.util.Coordinates;

public class NicholasTestHouseholdContext implements HouseholdContext {
    @Override
    public String getPlaceId() {
        return "EjA3NTIwIFNXIENhbnlvbiBDcmVzdCBEciwgUG9ydGxhbmQsIE9SIDk3MjI1LCBVU0E";
    }

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates(0, 0);
    }

    @Override
    public String getAddress() {
        return "7520 SW Canyon Crest Dr, Portland, OR";
    }

    @Override
    public List<UserContext> getResidents() {
        return Lists.newArrayList(Tests.juc, Tests.nuc);
    }
}
