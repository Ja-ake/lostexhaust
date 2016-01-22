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

import java.util.Map;

import com.jakespringer.lostexhaust.error.InvalidConfigurationException;

public class AsphaltConfiguration {
    public String googleApiKey;
    public String sqlHostname;
    public String sqlUsername;
    public String sqlPassword;
    
    public AsphaltConfiguration(Map<?, ?> conf) throws InvalidConfigurationException {
        if (!conf.containsKey("GoogleApiKey")) throw new InvalidConfigurationException("GoogleApiKey was not found.");
        
        googleApiKey = (String) conf.get("GoogleApiKey");
        sqlHostname = (String) conf.get("SqlHostname");
        sqlUsername = (String) conf.get("SqlUsername");
        sqlPassword = (String) conf.get("SqlPassword");
    }
}
