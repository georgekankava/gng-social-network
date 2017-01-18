package com.gng.network.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gng.network.enities.Message;
import com.gng.network.enities.User;
import com.gng.network.exceptions.ServiceException;
import com.gng.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gng.network.exceptions.MessageNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.MessageHelper;
import com.gng.network.service.MessageService;

import java.util.List;
import java.util.Locale;

@Controller
@Slf4j
public class MessageController {

    public static final String MESSAGE_PROCESSING_EXCEPTION_KEY = "message.processing.exception.key";

    @Inject
    private MessageSource messageSource;
    
    @Inject
    private MessageHelper messageHelper;
    
    @Inject
    private MessageService messageService;

    @Inject
    private UserService userService;
    
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@RequestMapping(method=RequestMethod.GET, value="/messages")
    public void getUserMessageList(HttpServletRequest httpServletRequest, @RequestParam("userId") Integer userId) {
	    try {
            List<User> users = userService.getUserMessageList(userId);
            if (!users.isEmpty()) {
                User user = users.get(0);
                List<Message> messages = messageService.getMessages(userId, user.getId(), 0);
                httpServletRequest.setAttribute("messages", messages);
            }
            httpServletRequest.setAttribute("users", users);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            httpServletRequest.setAttribute("errorMessage", messageSource.getMessage(e.getKeyCode(), null, null));
        } catch (Exception e) {
            log.error(e.getMessage());
            httpServletRequest.setAttribute("errorMessage", messageSource.getMessage(MESSAGE_PROCESSING_EXCEPTION_KEY, null, null));
        }
    }

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
