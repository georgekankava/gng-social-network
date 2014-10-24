package com.gng.network.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {
	public static FormUser getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	FormUser formUser = (FormUser)authentication.getPrincipal();
    	return formUser;
	}
}
