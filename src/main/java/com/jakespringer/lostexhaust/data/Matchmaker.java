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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Stream;
import com.jakespringer.lostexhaust.user.Account;
import com.jakespringer.lostexhaust.user.LeAccountService;

public class Matchmaker {
    public static final double MAX_MATCH_DISTANCE = 1.0f;
    
    private Metric<Coordinates> metric;
    
    public Matchmaker() {
        metric = new CoordinateMetric();
    }
    
    public MatchList findMatches(String id) {
        return findMatches(id, MAX_MATCH_DISTANCE);
    }
    
    public MatchList findMatches(String id, double maxDistance) {
        LinkedList<Match> matches = new LinkedList<>();
        Account origin = LeAccountService.getAccount(id);
        
        Stream<Account> allAccounts = LeAccountService.getAccountStream();
        allAccounts.forEach(c -> {
            if (c != origin) {
                float dist = metric.distance(origin.getCoordinates(), c.getCoordinates());
                if (dist <= maxDistance && dist > 0) {
                    matches.add(new Match(c.getId(), dist));
                }
            }
        });
        
        matches.sort(new Comparator<Match>() {
            @Override
            public int compare(Match m, Match n) {
                if (m.getDistance() > n.getDistance()) return 1;
                if (m.getDistance() < n.getDistance()) return -1;
                return 0;
            }
        });
        
        return new MatchList(id, matches);
    }
}
