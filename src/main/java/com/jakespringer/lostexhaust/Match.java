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

public class Match {
    private final String id;
    private float distance;
    
    public Match(String _id) {
        id = _id;
        setDistance(0.0f);
    }
    
    public Match(String _id, float _distance) {
        id = _id;
        setDistance(_distance);
    }
    
    public String getId() {
        return id;
    }
    
    public float getDistance() {
        return distance;
    }
    
    public void setDistance(float _distance) {
        if (_distance < 0.0) throw new IllegalArgumentException("Distance cannot be negative.");
        distance = _distance;
    }
}
