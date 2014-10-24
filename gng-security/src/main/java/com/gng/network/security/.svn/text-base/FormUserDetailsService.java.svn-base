package com.gng.network.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.service.UserService;

public class FormUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	
	@Autowired
	private UserHelper userHelper;
	
	private static final Logger logger = LoggerFactory.getLogger(FormUserDetailsService.class);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			com.gng.network.enities.User user = userService.findUserByUsername(username);
			FormUser userDetails = new FormUser(user.getId(), user.getUsername(), user.getPassword(), new ArrayList<FriendRequest>(user.getFriendRequests()), Collections.<GrantedAuthority>emptyList());
			userDetails.setFriends(new ArrayList<User>(user.getFriends()));
			userDetails.setFullname(user.getFullname());
			userHelper.setUserOnile(user);
		    return userDetails;
		} catch(UserNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
			
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		
		return roles;
	}
	
	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
	
}
