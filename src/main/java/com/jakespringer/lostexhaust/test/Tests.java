package com.jakespringer.lostexhaust.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import com.jakespringer.lostexhaust.LeWebserver;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.ContextCache;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.util.Coordinates;

public class Tests {
    public static final JakeTestHouseholdContext jhc = new JakeTestHouseholdContext();
    public static final NicholasTestHouseholdContext nhc = new NicholasTestHouseholdContext();
    public static final JakeTestSessionService jss = new JakeTestSessionService();
    public static final NicholasTestSessionService nss = new NicholasTestSessionService();
    public static final JakeTestUserContext juc = new JakeTestUserContext();
    public static final NicholasTestUserContext nuc = new NicholasTestUserContext();
    
    public static StaticHouseholdContext hstyles;
    public static StaticUserContext ustyles;
    public static StaticHouseholdContext hlopez;
    public static StaticUserContext ulopez;
    public static StaticHouseholdContext hdiggs;
    public static StaticUserContext udiggs;
    public static StaticHouseholdContext hgyllenhaal;
    public static StaticUserContext ugyllenhaal;
    public static StaticHouseholdContext hbrady;
    public static StaticUserContext ubrady;
    public static StaticHouseholdContext hbush;
    public static StaticUserContext ubush;
    public static StaticHouseholdContext hpage;
    public static StaticUserContext upage;
    public static StaticHouseholdContext hrihanna;
    public static StaticUserContext urihanna;
    public static StaticHouseholdContext hvergara;
    public static StaticUserContext uvergara;
    public static StaticHouseholdContext hcowell;
    public static StaticUserContext ucowell;
    
    public static void test() {
        try {
            List<UserContext> hstylesList = new ArrayList<>();
            hstyles = new StaticHouseholdContext("ChIJbyYs5Lq9woARGCPuoQHSAOU", new Coordinates(34.1166198,-118.4282923), "9551 Oak Pass Rd, Beverly Hills CA 90210", hstylesList);
            ustyles = new StaticUserContext(
                    "styles", "Harry", "Styles",
                    Lists.newArrayList(hstyles),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(juc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/styles.jpg"))
                );
            hstylesList.add(ustyles);
            hstylesList.add(juc);
            ContextCache.addUser(ustyles);
            ContextCache.addHousehold(hstyles);
            
            
            
            
            List<UserContext> hlopezList = new ArrayList<>();
            hlopez = new StaticHouseholdContext("ChIJda7CvwOewoARsOXdX1E0DiM", new Coordinates(34.1590284,-118.671013), "25067 Jim Bridger Rd, Hidden Hills CA 91302", hlopezList);
            ulopez = new StaticUserContext(
                    "lopez", "Jennifer", "Lopez",
                    Lists.newArrayList(hlopez),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(nuc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/lopez.jpg"))
                );
            hlopezList.add(ulopez);
            hlopezList.add(nuc);
            ContextCache.addUser(ulopez);
            ContextCache.addHousehold(hlopez);
            
            
            
            
            List<UserContext> hdiggsList = new ArrayList<>();
            hdiggs = new StaticHouseholdContext("ChIJO5FRUna-woARjOtCiNkJLk4", new Coordinates(34.1260934,-118.392789), "3121 Oakdell Ln, Studio City CA 91604", hdiggsList);
            udiggs = new StaticUserContext(
                    "diggs", "Taye", "Diggs",
                    Lists.newArrayList(hdiggs),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(juc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/diggs.jpg"))
                );
            hdiggsList.add(udiggs);
            hdiggsList.add(juc);
            hdiggsList.add(juc);
            hdiggsList.add(juc);
            ContextCache.addUser(udiggs);
            ContextCache.addHousehold(hdiggs);
            
            
            List<UserContext> hgyllenhaalList = new ArrayList<>();
            hgyllenhaal = new StaticHouseholdContext("ChIJBTaFA1e-woARMObwuJHeaVo", new Coordinates(34.1249714,-118.357476), "7411 Woodrow Wilson Dr, Los Angeles CA 90046", hgyllenhaalList);
            ugyllenhaal = new StaticUserContext(
                    "gyllenhaal", "Jake", "Gyllenhaal",
                    Lists.newArrayList(hgyllenhaal),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(nuc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/gyllenhaal.jpg"))
                );
            hgyllenhaalList.add(ugyllenhaal);
            hgyllenhaalList.add(nuc);
            hgyllenhaalList.add(nuc);
            hgyllenhaalList.add(nuc);
            hgyllenhaalList.add(nuc);
            hgyllenhaalList.add(nuc);
            ContextCache.addUser(ugyllenhaal);
            ContextCache.addHousehold(hgyllenhaal);
            
            
            
            List<UserContext> hbradyList = new ArrayList<>();
            hbrady = new StaticHouseholdContext("12780 Chalon Rd, Los Angeles CA 90049", new Coordinates(34.0826514,-118.499961), "12780 Chalon Rd, Los Angeles CA 90049", hbradyList);
            ubrady = new StaticUserContext(
                    "brady", "Tom", "Brady",
                    Lists.newArrayList(hbrady),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(juc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/brady.jpg"))
                );
            hbradyList.add(ubrady);
            hbradyList.add(juc);
            ContextCache.addUser(ubrady);
            ContextCache.addHousehold(hbrady);
            
            
            
            
            List<UserContext> hbushList = new ArrayList<>();
            hbush = new StaticHouseholdContext("ChIJd5KdwJm-woARyofkux_l_1I", new Coordinates(34.0982484,-118.384908), "1501 Viewsite Ter, Los Angeles CA 90069", hbushList);
            ubush = new StaticUserContext(
                    "bush", "Reggie", "Bush",
                    Lists.newArrayList(hbush),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(nuc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/bush.jpg"))
                );
            hbushList.add(ubush);
            hbushList.add(nuc);
            ContextCache.addUser(ubush);
            ContextCache.addHousehold(hbush);
            
            
            
            
            List<UserContext> hpageList = new ArrayList<>();
            hpage = new StaticHouseholdContext("ChIJxfixCW2-woARjPNYGzUP15w", new Coordinates(34.1339754,-118.381465), "11283 Canton Dr, Studio City CA 91604", hpageList);
            upage = new StaticUserContext(
                    "page", "Ellen", "Page",
                    Lists.newArrayList(hpage),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(juc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/page.jpg"))
                );
            hpageList.add(upage);
            ContextCache.addUser(upage);
            ContextCache.addHousehold(hpage);
            
            
            
            
            List<UserContext> hrihannaList = new ArrayList<>();
            hrihanna = new StaticHouseholdContext("ChIJWUb-xWajwoARUWrX0ra8jCk", new Coordinates(34.0469824,-118.515953), "932 Rivas Canyon Road, Pacific Palisades CA 90272", hrihannaList);
            urihanna = new StaticUserContext(
                    "rihanna", "Robyn", "(Rhianna) Fenty",
                    Lists.newArrayList(hrihanna),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(nuc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/rihanna.jpg"))
                );
            hrihannaList.add(urihanna);
            hrihannaList.add(nuc);
            ContextCache.addUser(urihanna);
            ContextCache.addHousehold(hrihanna);
            
            
            
            
            List<UserContext> hvergaraList = new ArrayList<>();
            hvergara = new StaticHouseholdContext("ChIJDfLL9ka8woARZSQcNOSGzXI", new Coordinates(34.0925824,-118.422409), "1156 San Ysidro Dr, Beverly Hills CA 90210", hvergaraList);
            uvergara = new StaticUserContext(
                    "vergara", "Sofia", "Vergara",
                    Lists.newArrayList(hvergara),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(juc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/vergara.jpg"))
                );
            hvergaraList.add(uvergara);
            hvergaraList.add(juc);
            ContextCache.addUser(uvergara);
            ContextCache.addHousehold(hvergara);
            
            
            
            
            List<UserContext> hcowellList = new ArrayList<>();
            hcowell = new StaticHouseholdContext("ChIJ7VcMziO8woAROPHe-17QUIo", new Coordinates(34.0932954,-118.401788), "917 Loma Vista Dr, Beverly Hills CA 90210", hcowellList);
            ucowell = new StaticUserContext(
                    "cowell", "Simon", "Cowell",
                    Lists.newArrayList(hcowell),
                    "Student", "11", "2017",
                    Lists.newArrayList(new Contact("Primary Email", "local@domain.tld")),
                    Lists.newArrayList(new Relationship(nuc, "Friend")),
                    Files.readAllBytes(Paths.get(LeWebserver.PUB_DIR+"img/cowell.jpg"))
                );
            hcowellList.add(ucowell);
            hcowellList.add(nuc);
            ContextCache.addUser(ucowell);
            ContextCache.addHousehold(hcowell);
            
            
            ContextCache.addUser(juc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
