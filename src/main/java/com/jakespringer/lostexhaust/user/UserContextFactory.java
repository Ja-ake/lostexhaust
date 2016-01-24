package com.jakespringer.lostexhaust.user;

import com.jakespringer.lostexhaust.test.Tests;

public class UserContextFactory {
    public static UserContext get(String id) {
        if (id == null) return null;
        
        switch (id) {
        case "ST16421": return Tests.juc;
        case "ST19029": return Tests.nuc;
        case "brady": return Tests.ubrady;
        case "bush": return Tests.ubush;
        case "cowell": return Tests.ucowell;
        case "diggs": return Tests.udiggs;
        case "gyllenhaal": return Tests.ugyllenhaal;
        case "lopez": return Tests.ulopez;
        case "page": return Tests.upage;
        case "rihanna": return Tests.urihanna;
        case "styles": return Tests.ustyles;
        case "vergara": return Tests.uvergara;
        default: return null;
        }
    }
}
