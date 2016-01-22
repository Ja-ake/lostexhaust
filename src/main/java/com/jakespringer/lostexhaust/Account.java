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
package com.jakespringer.lostexhaust;


public class Account {
    private String id;
    private String username;

    private String name;
    private Contact contact;
    private String notes;
    private Address address;
    private String placeId;
    private Coordinates coordinates;
    private String gradeLevel;
    
    public Account(String _id) {
        id = _id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }

    public Contact getContact() {
        return contact;
    }

    public String getNotes() {
        return notes;
    }

    public Address getAddress() {
        return address;
    }
    
    public String getPlaceId() {
        return placeId;
    }
    
    public String getGradeLevel() {
    	return gradeLevel;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setId(String _id) {
        id = _id;
    }
    
    public void setUsername(String username) throws InvalidAccountElementException {
        if (verifyUsername(username)) {
            this.username = username;;
        } else {
            throw new InvalidAccountElementException("Invalid username: " + username);
        }    
    }
    
    public void setName(String name) throws InvalidAccountElementException {
        if (verifyName(name)) {
            this.name = name;
        } else {
            throw new InvalidAccountElementException("Invalid name: " + name);
        }
    }

    public void setContact(Contact contact) throws InvalidAccountElementException {
        if (verifyContact(contact)) {
            this.contact = contact;
        } else {
            throw new InvalidAccountElementException("Invalid contact: " + contact);
        }
    }

    public void setNotes(String notes) throws InvalidAccountElementException {
        if (verifyName(notes)) {
            this.notes = notes;
        } else {
            throw new InvalidAccountElementException("Invalid notes: " + notes);
        }
    }

    public void setAddress(Address address) throws InvalidAccountElementException, InvalidPlaceException {
        if (verifyAddress(address)) {
            this.address = address;
        } else {
            throw new InvalidAccountElementException("Invalid address: " + address);
        }
    }
    
    public void setPlaceId(String placeId) throws InvalidAccountElementException, InvalidPlaceException {
        if (verifyPlaceId(placeId)) {
            this.placeId = placeId;
            setCoordinates(GoogleMaps.placeIdToCoordinates(placeId));
            setAddress(GoogleMaps.placeIdToAddress(placeId));
        } else {
            throw new InvalidAccountElementException("Invalid placeId: " + placeId);
        }
    }
    
    public void setPlaceIdPassive(String placeId) throws InvalidAccountElementException {
        if (verifyPlaceId(placeId)) {
            this.placeId = placeId;
        } else {
            throw new InvalidAccountElementException("Invalid placeId: " + placeId);
        }
    }

    public void setCoordinates(Coordinates coordinates) throws InvalidAccountElementException {
        if (verifyCoordinates(coordinates)) {
            this.coordinates = coordinates;
        } else {
            throw new InvalidAccountElementException("Invalid coordinates: " + coordinates);
        }
    }
    
    public void setGradeLevel(String gradeLevel) throws InvalidAccountElementException {
    	if (verifyGradeLevel(gradeLevel)) {
    		this.gradeLevel = gradeLevel;
    	} else {
    		throw new InvalidAccountElementException("Invalid grade level: " + gradeLevel);
    	}
    }
    
    public static boolean verifyUsername(String v) {
        return v != null && v.length() < 32;
    }
    
    public static boolean verifyName(String v) {
        return v != null && v.length() < 64;
    }
    
    public static boolean verifyContact(Contact v) {
        return v != null && v.getEmails().size() + v.getNumbers().size() < 6;
    }
    
    public static boolean verifyNotes(String v) {
        return v != null && v.length() < 501;
    }
    
    public static boolean verifyAddress(Address v) {
        // verified when setting the address fields
        return v != null && true;
    }
    
    public static boolean verifyPlaceId(String v) {
        // this seems reasonable, Google will do the rest
        return v != null && v.length() < 256;
    }
    
    public static boolean verifyCoordinates(Coordinates v) {
        // all coordinates are valid, even outside of [-2pi, 2pi]
        return v != null && true;
    }
    
    public static boolean verifyGradeLevel(String v) {
    	if (v == null) return false;
    	try {
    		int gl = Integer.parseInt(v);
    		return gl >= 1 && gl <= 12;
    	} catch (NumberFormatException e) {
    		return false;
    	}
    }
}
