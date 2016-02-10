package com.jakespringer.lostexhaust.data;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.user.UserContextFactory;
import com.jakespringer.lostexhaust.util.Coordinates;

public class CatlinHouseholdContext implements HouseholdContext {

    private final String householdId;
    private CatlinHousehold householdDetails;
    private List<UserContext> residents;

    public CatlinHouseholdContext(String id) {
        householdId = id;
    }

    public CatlinHouseholdContext(String _householdId, CatlinHousehold _householdDetails) {
        householdId = _householdId;
        householdDetails = _householdDetails;
    }

    @Override
    public String getId() {
        return householdId;
    }

    @Override
    public String getPlaceId() {
        ifNeededUpdateDetails();
        return householdDetails.placeId;
    }

    @Override
    public Coordinates getCoordinates() {
        ifNeededUpdateDetails();
        return householdDetails.coordinates;
    }

    @Override
    public String getAddress() {
        ifNeededUpdateDetails();
        return householdDetails.fullAddress;
    }

    @Override
    public List<UserContext> getResidents() {
        ifNeededUpdateResidents();
        return residents;
    }

    @Override
    public void invalidate() {
        householdDetails = null;
        residents = null;
    }

    private void ifNeededUpdateDetails() {
        if (householdDetails == null) {
            try {
                householdDetails = CatlinSql.inst.getHouseholdFromId(householdId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ifNeededUpdateResidents() {
        if (residents == null) {
            try {
                Collections.reverse(residents = CatlinSql.inst.getUsersFromHousehold(householdId).stream().map(UserContextFactory::get).collect(Collectors.toList()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
