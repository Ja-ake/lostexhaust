package com.jakespringer.lostexhaust.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jakespringer.lostexhaust.user.Contact;
import com.jakespringer.lostexhaust.user.Relationship;
import com.jakespringer.lostexhaust.util.Web;

public class CatlinApi {
    private static String apiToken = "";
    private static String photoToken = "";
    private static String apiUrl = "https://inside.catlin.edu/api/lostexhaust/api.py";
    private static String photoUrl = "https://inside.catlin.edu/api/lostexhaust/photo.php";
    
    private static Predicate<String> matchesId = Pattern.compile("^(ST|FS|IN)[0-9]{1,10}$").asPredicate();
    
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
                        
            return new CatlinPerson(personJson.getString("firstname"), 
                    personJson.getString("lastname"), 
                    personJson.getString("gradelevel"),
                    personJson.getString("classyear"),
                    personJson.getString("affiliation"),
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
    
    public static void main(String[]args) throws IOException {
//    	System.out.println("Completing.");
//        Files.write(getProfilePicture("ST16421"), new File("ST16421.jpg"));
//    	Files.write(Web.getRequestBytes("https://inside.catlin.edu/api/lostexhaust/photo.php?token=a92jHG6FqMXy71ZkJPoqajz915qaUTlsX7z&id=ST16421"), new File("me.jpg"));
    }
}
