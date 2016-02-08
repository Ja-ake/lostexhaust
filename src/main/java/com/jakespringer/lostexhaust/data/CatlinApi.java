package com.jakespringer.lostexhaust.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import com.jakespringer.lostexhaust.LeService;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.util.Timer;
import com.jakespringer.lostexhaust.util.Web;

import static com.jakespringer.lostexhaust.util.Quick.succOrNull;

public class CatlinApi {
    private static final String apiToken = LeService.getConfig().getString("catlin_api_token");
    private static final String photoToken = LeService.getConfig().getString("catlin_photo_token");
    private static final String apiUrl = LeService.getConfig().getString("catlin_api_url");
    private static final String photoUrl = LeService.getConfig().getString("catlin_photo_url");
    
    public static final Predicate<String> matchesId = Pattern.compile("^(ST|FS|IN)[0-9]{1,10}$").asPredicate();
    
    public static List<CatlinPerson> getPeople(String... userId) throws IOException {
//        Timer t = Timer.start("getPerson("+Arrays.toString(userId)+")");
        if (Arrays.stream(userId).allMatch(matchesId::test)) {
            List<CatlinPerson> people = new ArrayList<>();
            String getResult = Web.getRequest(apiUrl+"?token="+apiToken+"&query=person&id="+String.join(",", userId));
            JSONArray peopleArray = new JSONArray(getResult);
            peopleArray.forEach(k -> {
                JSONObject o = (JSONObject) k;
                List<Contact> contacts = new ArrayList<>();
                ((JSONArray) o.get("contacts")).forEach(x -> 
                        contacts.add(new Contact( ((JSONObject)x).getString("contact_type") + " " +
                                ((JSONObject)x).getString("contact_mode") , ((JSONObject)x).getString("contact_value") )));
                
                List<Relationship> relationships = new ArrayList<>();
                ((JSONArray) o.get("relationships")).forEach(x -> 
                        relationships.add(new Relationship( new CatlinUserContext(((JSONObject)x).getString("relation_id")), 
                                ((JSONObject)x).getString("relationship"), 
                                ((JSONObject)x).getString("relation_firstname"), 
                                ((JSONObject)x).getString("relation_lastname") )));
                
                people.add(new CatlinPerson(
                      succOrNull(() -> o.getString("eeid")),
                      succOrNull(() -> o.getString("firstname")), 
                      succOrNull(() -> o.getString("lastname")), 
                      succOrNull(() -> o.getString("gradelevel")),
                      succOrNull(() -> o.getString("classyear")),
                      succOrNull(() -> o.getString("affiliation")),
                      contacts,
                      relationships));
            });

//            t.stop();
            return people;
        } else {
            throw new RuntimeException("Invalid userId: " + userId);
        }  
    }
    
    public static byte[] getProfilePicture(String userId) throws IOException {
//        Timer t = Timer.start("getProfilePicture("+userId+")");
    	if (matchesId.test(userId)) {
    	    byte[] ret = Web.getRequestBytes(photoUrl + "?token=" + photoToken + "&id=" + userId);
//    		t.stop();
    	    return ret;
    	} else {
            throw new RuntimeException("Invalid userId: " + userId);
        }
    }
}
