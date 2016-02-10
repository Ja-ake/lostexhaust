package com.jakespringer.lostexhaust.near;

import com.jakespringer.lostexhaust.user.HouseholdContext;

public class Carpool {
    public final HouseholdContext household;
    public final double distance;

    public Carpool(HouseholdContext _household, double _distance) {
        household = _household;
        distance = _distance;
    }
}
