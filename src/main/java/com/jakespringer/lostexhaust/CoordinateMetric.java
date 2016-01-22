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

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

public class CoordinateMetric implements Metric<Coordinates> {

    private static GeodeticCalculator geoCalc = new GeodeticCalculator();
    
    /**
     * Returns bird's eye distance in kilometers.
     * @see Metric#distance(Object, Object)
     */
    @Override
    public float distance(Coordinates srcCoord, Coordinates dstCoord) {
        Ellipsoid reference = Ellipsoid.WGS84;  
        GlobalPosition pointA = new GlobalPosition(srcCoord.latitude, srcCoord.longitude, 0.0); // Point A
        GlobalPosition pointB = new GlobalPosition(dstCoord.latitude, dstCoord.longitude, 0.0); // Point B

        return (float) (geoCalc.calculateGeodeticCurve(reference, pointB, pointA).getEllipsoidalDistance() * 1.0e-3);
    }
}
