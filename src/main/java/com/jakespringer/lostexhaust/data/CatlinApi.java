package com.jakespringer.lostexhaust.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import com.jakespringer.lostexhaust.LeService;
import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.util.Web;

import static com.jakespringer.lostexhaust.util.Quick.succOrNull;

public class CatlinApi {
    private static final String apiToken = LeService.getConfig().getString("catlin_api_token");
    private static final String photoToken = LeService.getConfig().getString("catlin_photo_token");
    private static final String apiUrl = LeService.getConfig().getString("catlin_api_url");
    private static final String photoUrl = LeService.getConfig().getString("catlin_photo_url");
    
    public static final Predicate<String> matchesId = Pattern.compile("^(ST|FS|IN)[0-9]{1,10}$").asPredicate();
    
    public static CatlinPerson getPerson(String userId) throws IOException {
        if (matchesId.test(userId)) {
            String getResult = Web.getRequest(apiUrl+"?token="+apiToken+"&query=person&id="+userId);
            JSONObject personJson = new JSONObject(getResult);
            
            List<Contact> contacts = new ArrayList<>();
            ((JSONArray) personJson.get("contacts")).forEach(x -> 
                    contacts.add(new Contact( ((JSONObject)x).getString("contact_type") + " " +
                            ((JSONObject)x).getString("contact_mode") , ((JSONObject)x).getString("contact_value") )));
            
            List<Relationship> relationships = new ArrayList<>();
            ((JSONArray) personJson.get("relationships")).forEach(x -> 
                    relationships.add(new Relationship( new CatlinUserContext(((JSONObject)x).getString("relation_id")), 
                            ((JSONObject)x).getString("relationship"), 
                            ((JSONObject)x).getString("relation_firstname"), 
                            ((JSONObject)x).getString("relation_lastname") )));
            
            
            // temp solution, todo remove succOrNull
            return new CatlinPerson(
                    succOrNull(() -> personJson.getString("firstname")), 
                    succOrNull(() -> personJson.getString("lastname")), 
                    succOrNull(() -> personJson.getString("gradelevel")),
                    succOrNull(() -> personJson.getString("classyear")),
                    succOrNull(() -> personJson.getString("affiliation")),
                    contacts,
                    relationships);
        } else {
            throw new RuntimeException("Invalid userId: " + userId);
        }        
    }
    
    public static byte[] getProfilePicture(String userId) throws IOException {
    	if (matchesId.test(userId)) {
    		return Web.getRequestBytes(photoUrl + "?token=" + photoToken + "&id=" + userId);
    	} else {
            throw new RuntimeException("Invalid userId: " + userId);
        }
    }
}
