package com.jakespringer.lostexhaust.util;

public class Coordinates {
    public double latitude;
    public double longitude;
    
    public Coordinates(double _lat, double _long) {
        latitude = _lat;
        longitude = _long;
    }
    
    public double distanceTo(Coordinates other) {
        // trust that it works (hopefully)
        double lat = Math.toRadians(latitude);
        double lon = Math.toRadians(longitude);
        double lat2 = Math.toRadians(other.latitude);
        double lon2 = Math.toRadians(other.longitude);
        double deltaLat = lat2 - lat;
        double deltaLon = lon2 - lon;
        double sinPart = (Math.sin(deltaLat/2.0d));
        double cosPart = (Math.cos(lat)*Math.cos(lat2)*Math.sin(deltaLon/2.0d));
        double angle = 2.0d * Math.asin(Math.sqrt(sinPart*sinPart + cosPart*cosPart));
        return 3960.0d * angle;
    }
}
