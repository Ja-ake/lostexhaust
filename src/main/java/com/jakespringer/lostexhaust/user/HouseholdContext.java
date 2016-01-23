package com.jakespringer.lostexhaust.user;

import java.util.List;
import com.jakespringer.lostexhaust.util.Coordinates;

public interface HouseholdContext {
    public String getPlaceId();
    public Coordinates getCoordinates();
    public String getAddress();
    public List<UserContext> getResidents();
}
