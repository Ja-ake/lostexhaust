package com.jakespringer.lostexhaust.test;

import java.util.List;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.util.Coordinates;

public class StaticHouseholdContext implements HouseholdContext {

    private String placeId;
    private Coordinates coordinates;
    private String address;
    private List<UserContext> residents;
    
    public StaticHouseholdContext(String _placeId, Coordinates _coordinates, String _address, List<UserContext> _residents) {
        placeId = _placeId;
        coordinates = _coordinates;
        address = _address;
        residents = _residents;
    }
    
    @Override
    public String getPlaceId() {
        return placeId;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public List<UserContext> getResidents() {
        return residents;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

}
