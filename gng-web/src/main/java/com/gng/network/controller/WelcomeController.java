package com.gng.network.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gng.network.forms.UserSignupData;
import com.gng.network.service.UserService;


@Controller
public class WelcomeController {
    
    @Inject
    UserService userService; 
    
    @Inject
    MessageSource messageSource;
    
    @RequestMapping(method={RequestMethod.GET}, value="/index.html")
    public String welcome(HttpServletRequest request, HttpServletResponse response, 
    		@ModelAttribute("userSignupData") UserSignupData userSignupData) {
    	return "welcome";
    }
    
    public String processLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
