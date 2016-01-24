package com.jakespringer.lostexhaust.near;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.util.Coordinates;

public class CarpoolSorter {
    public static List<Carpool> sort(HouseholdContext origin, List<HouseholdContext> others) {        
        Coordinates o = origin.getCoordinates();
        List<Carpool> unorderedCarpools = others.stream().map(i -> new Carpool(i, Math.floor(i.getCoordinates().distanceTo(o)*100)/100.0))
                    .collect(Collectors.toList());
   
        // order the unordered carpools
        unorderedCarpools.sort((a, b) -> (int) -Math.signum(b.distance - a.distance));
        return Collections.unmodifiableList(unorderedCarpools);
    }
}
