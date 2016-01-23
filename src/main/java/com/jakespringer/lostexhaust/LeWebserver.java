/* 
 * Copyright (c) 2016 by Jake Springer
 * This file may only be copied or distributed under the conditions of the license,
 * which should have been distributed with your copy of this source code.
 * 
 * Written by Jake Springer in 2016.
 */

package com.jakespringer.lostexhaust;
import static spark.Spark.get;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import com.jakespringer.lostexhaust.test.JakeTestSessionService;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.SessionService;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.user.UserContextFactory;
import com.jakespringer.lostexhaust.user.UserSession;
import com.jakespringer.lostexhaust.util.Pebble2TemplateEngine;
import spark.ModelAndView;

public class LeWebserver {
	public static final String REQ_APP_DIR = "/" + System.getProperty("user.dir").substring(1) + "/src/main/webapp/public/";
	
	public static void main(String[] args) {
	    Pebble2TemplateEngine pebbleEngine = new Pebble2TemplateEngine();
	    
	    SessionService sessionService = new JakeTestSessionService();
	    
	    get("/", (req, res) -> {
	        return "Welcome to <a href=\"https://www.lostexhaust.org/\">Lost Exhaust!</a>";
	    });
	    
        get("/near.html", (req, res) -> {
        	Map<String, Object> context = new HashMap<>();
        	UserSession userSession = sessionService.getSession(req.cookie("session"));
        	UserContext user = userSession.getContext();
        	return new ModelAndView(context, REQ_APP_DIR + "near.peb");
        }, pebbleEngine);
        
        get("/household.html", (req, res) -> {
            Map<String, Object> context = new HashMap<>();
            UserSession userSession = sessionService.getSession(req.cookie("session"));
            UserContext user = userSession.getContext();
            HouseholdContext household = user.getHouseholds().get(0);
            context.put("nickname", user.getFirstname());
            context.put("address", household.getAddress());
            context.put("place_id", household.getPlaceId());
            context.put("inhabitants", household.getResidents());
            return new ModelAndView(context, REQ_APP_DIR + "household.peb");
        }, pebbleEngine);
        
        get("/profile.html", (req, res) -> {
            Map<String, Object> context = new HashMap<>();
            UserSession userSession = sessionService.getSession(req.cookie("session"));
            UserContext user = userSession.getContext();
            context.put("user", user);
            return new ModelAndView(context, REQ_APP_DIR + "profile.peb");
        }, pebbleEngine);
        
        get("/person.html", (req, res) -> {
            Map<String, Object> context = new HashMap<>();
            UserSession userSession = sessionService.getSession(req.cookie("session"));
            UserContext user = userSession.getContext();
            String p = req.queryParams("p");
            context.put("user", user);
            context.put("person", UserContextFactory.get(p));
            return new ModelAndView(context, REQ_APP_DIR + "person.peb");
        }, pebbleEngine);
        
        get("/img/me.jpg", (req, res) -> {
            return sessionService
                    .getSession(req.cookie("session"))
                    .getContext()
                    .getProfilePicture();
        });
        
        get("/login", (req, res) -> {
            res.redirect("/near");
            return "You are being redirected. Please wait a moment.";
        });
        
        get("/logout", (req, res) -> {
            res.redirect("https://www.lostexhaust.org/");
            return "You are being redirected. Please wait a moment."; 
        });
        
        get("/*", (req, res) -> {            
            String path = req.splat()[0];
            if (LeService.suffixMatches("/"+path,
                    "htm", "html", "jpg", "jpeg", "png", "js", "css")) {
                try {
                    return Files.readAllBytes(Paths.get(REQ_APP_DIR, path));
                } catch (NoSuchFileException | FileNotFoundException e) {
                    return "<h2>404 Not Found.</h2>";
                }
            } else {
                return "<h1>You are not permitted to access this resource.</h1>";
            }
        });
    }
}

