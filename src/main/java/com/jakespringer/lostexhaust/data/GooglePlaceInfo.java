/*
 * Asphalt - A simple carpool matching service
 * Copyright (c) 2015 Jake Springer
 *
 * This file is part of Asphalt.
 *
 * Asphalt is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Asphalt is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Asphalt.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jakespringer.lostexhaust.data;

import com.jakespringer.lostexhaust.error.InvalidAddressElementException;


public class GooglePlaceInfo {
    public final String placeId;
    public final String streetNumber;
    public final String route;
    public final String locality;
    public final String adminArea;
    public final String postalCode;
    public final String country;
    public final double latitude;
    public final double longitude;
    
    public GooglePlaceInfo(String _placeId,
            String _streetNumber,
            String _route,
            String _locality,
            String _adminArea,
            String _postalCode,
            String _country,
            double _latitude,
            double _longitude) {
        placeId = _placeId;
        streetNumber = _streetNumber;
        route = _route;
        locality = _locality;
        adminArea = _adminArea;
        postalCode = _postalCode;
        country = _country;
        latitude = _latitude;
        longitude = _longitude;
    }
    
    public Address toAddress() throws InvalidAddressElementException {
        return new Address(streetNumber, route, locality, adminArea, postalCode, country);
    }
    
    public Coordinates toCoordinates() {
        return new Coordinates(latitude, longitude);
    }
}
