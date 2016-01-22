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

/**
 * A metric is an interface that defines a single function,
 * {@link #distance(Object, Object)}. The distance function should follow the
 * following three properties of mathematical two- dimensional metrics:<br>
 * For any a: U, b: U, c: U =><br>
 * 1) distance(a, b) = distance(b, a)<br>
 * 2) distance(a, a) = distance(b, b) = 0.0<br>
 * 3) distance(a, b) + distance(b, c) >= distance(a, c)<br>
 * All other specifications of the metric are implementation defined.
 */
public interface Metric<U> {
    public float distance(U source, U destination);
}
