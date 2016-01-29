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
import java.util.List;
import java.util.Map;

import com.jakespringer.lostexhaust.near.Carpool;
import com.jakespringer.lostexhaust.near.CarpoolSorter;
import com.jakespringer.lostexhaust.test.Tests;
import com.jakespringer.lostexhaust.user.ContextCache;
import com.jakespringer.lostexhaust.user.HouseholdContext;
import com.jakespringer.lostexhaust.user.HouseholdContextFactory;
import com.jakespringer.lostexhaust.user.SessionService;
import com.jakespringer.lostexhaust.user.UserContext;
import com.jakespringer.lostexhaust.user.UserContextFactory;
import com.jakespringer.lostexhaust.user.UserSession;
import com.jakespringer.lostexhaust.util.Pebble2TemplateEngine;
import com.jakespringer.lostexhaust.auth.*;

import spark.ModelAndView;
import spark.Spark;

public class LeWebserver {
	public static final String PUB_DIR = System.getProperty("user.dir") + "/src/main/webapp/public/";
	public static final String CONF_DIR = System.getProperty("user.dir") + "/src/main/webapp/config/";
	
	public static void main(String[] args) {
		CatlinCrypto.test();
		System.out.println(System.getProperty("java.vendor") + " " + System.getProperty("java.version"));
		// deal with arguments
		int port = 4567;
		boolean pflag = false;
		for (String s : args) {
			if (s.equals("-p")) {
				pflag = true;
				continue;
			}
			
			if (pflag) {
				try {
					port = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					System.out.println("Invalid port number: " + s);
					System.exit(0);
				}
				pflag = false;
				continue;
			}
		}
		
		Tests.test();
		
		// ignite spark
	    Spark.port(port);
		
		Pebble2TemplateEngine pebbleEngine = new Pebble2TemplateEngine();
	    SessionService sessionService = new CatlinSessionService();
	    
	    get("/", (req, res) -> {
	        return Files.readAllBytes(Paths.get(PUB_DIR + "embed.html"));
	    });
	    
        get("/near.html", (req, res) -> {
        	UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
        	if (userSession!=null) {
        		System.out.println("Session ip: "+userSession.getIp() + " " + req.ip());
        	} else {
        		System.out.println("Cookie: %" + req.cookie("session") + "%");
        		System.out.println("Invalid cookie: " + CatlinCrypto.decryptRSA(req.cookie("session"), CONF_DIR + "public.der"));
        	}
	        if (userSession != null) {
	        	Map<String, Object> context = new HashMap<>();
        		UserContext user = userSession.getContext();
	        	HouseholdContext origin;
	        	String h = req.queryParams("h");
	        	if (h == null || h.isEmpty()) origin = user.getHouseholds().get(0);
	        	else origin = user.getHouseholds().get(Integer.parseInt(h));
	        	List<Carpool> sorted = CarpoolSorter.sort(origin, 
	        	        ContextCache.getHouseholds());
	        	if (h == null || h.isEmpty()) context.put("h", 0);
	        	else context.put("h", Integer.parseInt(h));
	        	context.put("user", user);
	        	context.put("sorted", sorted);
	        	return new ModelAndView(context, PUB_DIR + "near.peb");
	        } else {
	        	return new ModelAndView(null, PUB_DIR + "invalidsession.peb");
	        }
        }, pebbleEngine);
        
        get("/home.html", (req, res) -> {
            UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	            Map<String, Object> context = new HashMap<>();
	        	UserContext user = userSession.getContext();
		        HouseholdContext household;
		    	String h = req.queryParams("h");
		    	if (h == null || h.isEmpty()) household = user.getHouseholds().get(0);
		    	else household = user.getHouseholds().get(Integer.parseInt(h));
		    	if (h == null || h.isEmpty()) context.put("h", 0);
		    	else context.put("h", Integer.parseInt(h));
		    	context.put("user", user);
		        context.put("nickname", user.getFirstname());
		        context.put("address", household.getAddress());
		        context.put("place_id", household.getPlaceId());
		        context.put("inhabitants", household.getResidents());
		        return new ModelAndView(context, PUB_DIR + "home.peb");
	        } else {
	        	return new ModelAndView(null, PUB_DIR + "invalidsession.peb");
	        }
        }, pebbleEngine);
        
        get("/household.html", (req, res) -> {
            UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	            Map<String, Object> context = new HashMap<>();
	            UserContext user = userSession.getContext();
	            String h = req.queryParams("h");
	            HouseholdContext household;
	            if (h == null || h.isEmpty()) household = user.getHouseholds().get(0);
	        	else household = HouseholdContextFactory.get(h);
	            //context.put("user", user);
	            context.put("nickname", user.getFirstname());
	            context.put("address", household.getAddress());
	            context.put("place_id", household.getPlaceId());
	            context.put("inhabitants", household.getResidents());
	            return new ModelAndView(context, PUB_DIR + "household.peb");
	        } else {
	        	return new ModelAndView(null, PUB_DIR + "invalidsession.peb");
	        }
        }, pebbleEngine);
        
        get("/profile.html", (req, res) -> {
        	UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	        	Map<String, Object> context = new HashMap<>();
	            UserContext user = userSession.getContext();
	            context.put("user", user);
	            return new ModelAndView(context, PUB_DIR + "profile.peb");
	        } else {
	        	return new ModelAndView(null, PUB_DIR + "invalidsession.peb");
	        }
        }, pebbleEngine);
        
        get("/person.html", (req, res) -> {
            Map<String, Object> context = new HashMap<>();
            UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	            UserContext user = userSession.getContext();
	            String p = req.queryParams("p");
	            context.put("user", user);
	            context.put("person", UserContextFactory.get(p));
	            return new ModelAndView(context, PUB_DIR + "person.peb");
	        } else {
	        	return new ModelAndView(null, PUB_DIR + "invalidsession.peb");
	        }
        }, pebbleEngine);
        
        get("/img/me.jpg", (req, res) -> {
        	UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	        	return sessionService
                    .getSession(req.cookie("session"), req.ip())
                    .getContext()
                    .getProfilePicture();
	        } else {
	        	return new byte[0];
	        }
        });
        
        get("/login", (req, res) -> {
            String token = req.queryParams("token");
            res.header("Expires", "Thu, 19 Nov 1981 08:52:00 GMT");
            res.header("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
            res.header("Pragma", "no-cache");
            if (token != null && !token.isEmpty()) {
                res.cookie("session", token);
            }
            res.redirect("/");
            return "You are being redirected. Please wait a moment.";
        });
        
        get("/logout", (req, res) -> {
            res.removeCookie("session");
            res.redirect("/");
            return "You are being redirected. Please wait a moment."; 
        });
        
        get("/*", (req, res) -> {            
        	UserSession userSession = sessionService.getSession(req.cookie("session"), req.ip());
	        if (userSession != null) {
	        	String path = req.splat()[0];
	            if (LeService.suffixMatches("/"+path,
	                    "htm", "html", "jpg", "jpeg", "png", "js", "css")) {
	                try {
	                    return Files.readAllBytes(Paths.get(PUB_DIR, path));
	                } catch (NoSuchFileException | FileNotFoundException e) {
	                    return "<h2>404 Not Found.</h2>";
	                }
	            } else {
	                return "<h1>You are not permitted to access this resource.</h1>";
	            }
	        } else {
	        	return "<h1>You are not permitted to access this resource.</h1>";
	        }
        });
    }
}

