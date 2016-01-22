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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MatchList {
    private final String originId;
    private List<Match> matches;
    
    public MatchList(String _originId, Match... _matches) {
        originId = _originId;
        matches = Arrays.asList(_matches);
    }
    
    public MatchList(String _originId, Collection<Match> _matches) {
        originId = _originId;
        matches = new ArrayList<>(_matches);
    }
    
    public void add(Match match) {
        matches.add(match);
    }
    
    public void remove(Match match) {
        matches.remove(match);
    }
    
    public Iterator<Match> matchIterator() {
        return matches.iterator();
    }
    
    public List<Match> getMatchList() {
        return Collections.unmodifiableList(matches);
    }
}
