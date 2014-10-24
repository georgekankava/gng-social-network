package com.gng.network.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.service.UserService;


public class UserLogoutHandler implements LogoutSuccessHandler {
	
	@Inject
	private UserHelper userHelper;
	
	@Inject
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserLogoutHandler.class);
	

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(authentication != null) {
			try {
				FormUser formUser = (FormUser)authentication.getPrincipal();
				User user = userService.findUserById(formUser.getUserId());
				userHelper.setUserOffline(user);
			} catch (UserNotFoundException e) {
				logger.info(e.getMessage());
			}
		}
	}

}
