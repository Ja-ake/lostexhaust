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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LeService {
    public static long getValidityDuration() {
        // 12 hours
        // TODO add to configuration
        return 43200L;
    }
    
    public static String fetchUrl(String urlName) throws IOException {
        StringBuilder b = new StringBuilder();
        URL url = new URL(urlName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            b.append(line);
        }
        reader.close();
        
        return b.toString();
    }
    
    public static String getFileSuffix(String path) {
        int i = path.lastIndexOf('.');
        int p = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

        if (i > p) return path.substring(i+1);
        else return "";
    }
    
    public static boolean suffixMatches(String path, String... toMatch) {
        String suffix = getFileSuffix(path);
        for (String m : toMatch) if (suffix.equalsIgnoreCase(m)) return true;
        return false;
    }
}
