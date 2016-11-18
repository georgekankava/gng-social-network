package com.gng.network.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreetController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(GreetController.class);
	
	@RequestMapping(value = "greet.html", method = RequestMethod.GET)
	public String greet(Model model) {
		return "greet";
	}
	
}
