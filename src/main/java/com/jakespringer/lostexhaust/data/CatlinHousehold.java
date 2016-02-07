package com.jakespringer.lostexhaust.data;

import com.jakespringer.lostexhaust.util.Coordinates;

public class CatlinHousehold {
    public final String id;
    public final String placeId;
    public final Coordinates coordinates;
    public final String fullAddress;
    
    public CatlinHousehold(String _id, String _placeId, double _lat, double _lng, String _fullAddress) {
        id = _id;
        placeId = _placeId;
        coordinates = new Coordinates(_lat, _lng);
        fullAddress = _fullAddress;
    }
}
