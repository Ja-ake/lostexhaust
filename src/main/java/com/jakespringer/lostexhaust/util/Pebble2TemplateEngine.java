/* 
 * Copyright (c) 2016 by Jake Springer
 * This file may only be copied under the conditions of the license,
 * which should have been distributed with your copy of this source code.
 * 
 * Written by Jake Springer in 2016.
 */

package com.jakespringer.lostexhaust.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import spark.ModelAndView;
import spark.TemplateEngine;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class Pebble2TemplateEngine extends TemplateEngine {

	/**
	 * The Pebble Engine instance.
	 */
	private final PebbleEngine engine;

	/**
	 * Construct a new template engine using pebble with a default engine.
	 */
	public Pebble2TemplateEngine() {
		this.engine = (new PebbleEngine.Builder()).build();
	}

	/**
	 * Construct a new template engine using pebble with a specified engine.
	 *
	 * @param engine The pebble template engine.
	 */
	public Pebble2TemplateEngine(PebbleEngine engine) {
		this.engine = engine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String render(ModelAndView modelAndView) {
		Object model = modelAndView.getModel();

		if (model == null || model instanceof Map) {
			try {
				StringWriter writer = new StringWriter();

				PebbleTemplate template = engine.getTemplate(modelAndView.getViewName());
				if (model == null) {
					template.evaluate(writer);
				} else {
					template.evaluate(writer, (Map<String, Object>) modelAndView.getModel());
				}

				return writer.toString();
			} catch (PebbleException | IOException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new IllegalArgumentException("Invalid model, model must be instance of Map.");
		}
	}
}
