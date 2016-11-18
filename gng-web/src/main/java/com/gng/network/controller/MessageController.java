package com.gng.network.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gng.network.exceptions.MessageNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.MessageHelper;
import com.gng.network.service.MessageService;

@Controller
public class MessageController {

	@Inject
    MessageSource messageSource;
    
    @Inject
    MessageHelper messageHelper;
    
    @Inject
    MessageService messageService;
    
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
    
    /**
     * Selects the home page and populates the model with a message 
     */
    @ResponseBody
    @RequestMapping("/get-messages.ajax")
    public String home(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, 
    		@RequestParam Integer userFromId, @RequestParam Integer userToId, @RequestParam Long fromMillies) {
    	try {
			return messageService.getJsonMessages(userFromId, userToId, fromMillies);
		} catch (UserNotFoundException e) {
			logger.info(e.getMessage());
		} catch (MessageNotFoundException e) {
			logger.info(e.getMessage());
		}
    	return "";
    }
}
