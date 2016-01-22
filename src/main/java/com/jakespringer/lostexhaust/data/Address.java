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


public class Address {
    private String streetNumber;
    private String route;
    private String locality;
    private String adminArea;
    private String postalCode;
    private String country;

    public Address(String _streetNumber, String _route, 
            String _locality, String _adminArea, String _postalCode, 
            String _country) throws InvalidAddressElementException {
        
        setStreetNumber(_streetNumber);
        setRoute(_route);
        setLocality(_locality);
        setAdminArea(_adminArea);
        setPostalCode(_postalCode);
        setCountry(_country);
    }
    
    public String getLocality() {
        return locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public String getCountry() {
        return country;
    }

    public String getRoute() {
        return route;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setLocality(String _locality) throws InvalidAddressElementException {
        if (verifyLocality(_locality)) {
            locality = _locality;
        } else {
            throw new InvalidAddressElementException("Invalid locality: " + _locality);
        }
    }

    public void setPostalCode(String _postalCode) throws InvalidAddressElementException {
        if (verifyPostalCode(_postalCode)) {
            postalCode = _postalCode;
        } else {
            throw new InvalidAddressElementException("Invalid postal code: " + _postalCode);
        }
    }

    public void setAdminArea(String _adminArea) throws InvalidAddressElementException {
        if (verifyAdminArea(_adminArea)) {
            adminArea = _adminArea;
        } else {
            throw new InvalidAddressElementException("Invalid administration area: " + _adminArea);
        }
    }

    public void setCountry(String _country) throws InvalidAddressElementException {
        if (verifyCountry(_country)) {
            country = _country;
        } else {
            throw new InvalidAddressElementException("Invalid country: " + _country);
        }
    }

    public void setRoute(String _route) throws InvalidAddressElementException {
        if (verifyRoute(_route)) {
            route = _route;
        } else {
            throw new InvalidAddressElementException("Invalid route: " + _route);
        }
    }

    public void setStreetNumber(String _streetNumber) throws InvalidAddressElementException {
        if (verifyStreetNumber(_streetNumber)) {
            streetNumber = _streetNumber;
        } else {
            throw new InvalidAddressElementException("Invalid street number: " + _streetNumber);
        }
    }
    
    public static boolean verifyLocality(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 200;
    }
    
    public static boolean verifyPostalCode(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 32;
    }
    
    public static boolean verifyAdminArea(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 64;
    }
    
    public static boolean verifyCountry(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 64;
    }
    
    public static boolean verifyRoute(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 200;
    }
    
    public static boolean verifyStreetNumber(String v) {
        // TODO: verify that this is an acceptable limit
        return v.length() < 32;
    }
}
