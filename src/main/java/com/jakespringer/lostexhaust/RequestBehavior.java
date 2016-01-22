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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class RequestBehavior {
    private final String uri;
    
    public RequestBehavior(String _uri) {
        uri = _uri;
    }
    
    public String getUri() {
        return uri;
    }
    
    @Override
    public boolean equals(Object o) {
        return (o.getClass().equals(getClass())) && ((RequestBehavior)o).uri.equals(uri);
    }
    
    public abstract void behavior(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
