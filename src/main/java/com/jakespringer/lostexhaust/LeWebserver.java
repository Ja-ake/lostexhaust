/* 
 * Copyright (c) 2016 by Jake Springer
 * This file may only be copied or distributed under the conditions of the license,
 * which should have been distributed with your copy of this source code.
 * 
 * Written by Jake Springer in 2016.
 */

package com.jakespringer.lostexhaust;
import static spark.Spark.get;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;

import com.jakespringer.lostexhaust.util.Pebble2TemplateEngine;

public class LeWebserver {
	public static final String APP_DIR = "/src/main/webapp/";
	
	public static void main(String[] args) {
        get("/hello", (req, res) -> {
        	Map<String, Object> context = new HashMap<>();
        	context.put("message", "Hi there!");
        	return new ModelAndView(context, "/" + System.getProperty("user.dir").substring(1) + APP_DIR + "hello.pbl");
        }, new Pebble2TemplateEngine());
        
//        get("")
    }
}

