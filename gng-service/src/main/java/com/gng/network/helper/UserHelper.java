package com.gng.network.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import com.gng.network.enities.Address;
import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.forms.UserSignupData;
import com.gng.network.json.response.UsersResponseJson;
import com.gng.network.service.UserService;

@Component
public class UserHelper {
	
	@Inject
	private UserService userService;
	
	@Inject
	private ObjectMapper mapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);
	
	public User userSignupDataToUser(UserSignupData userSignupData) throws IOException, NoSuchAlgorithmException {
		User user = new User();
		user.setUsername(userSignupData.getUsername());
		user.setLastname(userSignupData.getLastname());
		user.setPassword(passwordDigest(userSignupData.getPassword()));
		user.setFirstname(userSignupData.getFirstname());
		user.setFullname(userSignupData.getFirstname() + " " + userSignupData.getLastname());
		user.setOnline(true);
		return user;
	}
	
	public List<User> findUser(String username) throws UserNotFoundException {
		User user = userService.findUserByUsername(username);
		if(user != null) {
			return Arrays.<User>asList(user);
		}
		return userService.findUserByFullname(username);
	}
	
	public String passwordDigest(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		byte [] digest = md.digest(password.getBytes());
		return new String(Hex.encode(digest)); 
	}
	
	public List<Integer> getUsersIds(List<User> users) {
		List<Integer> ids = new ArrayList<Integer>();
    	for(User friend : users) {
    		ids.add(friend.getId());
    	}
    	return ids;
	}
	
	public List<UsersResponseJson> convertUsersToJsonUsers(List<User> users) {
		List<UsersResponseJson> convertedUsers = new ArrayList<UsersResponseJson>();
		for (User user : users) {
			UsersResponseJson cUser = new UsersResponseJson();
			cUser.setUserId(user.getId());
			cUser.setFullname(user.getFullname());
			cUser.setUsername(user.getUsername());
			cUser.setProfileImage(user.getProfileImage());
			if(user.getAddresses().size() > 0) {
				Address userAddress = user.getAddresses().get(0);
				cUser.setCity(userAddress.getCity());
				cUser.setCountry(userAddress.getCountry());
			}
			convertedUsers.add(cUser);
		}
		return convertedUsers;
	}
	
	public String convertUsersToJsonString(List<UsersResponseJson> users) {
		try {
			String jsonUsers = mapper.writeValueAsString(users);
			return jsonUsers;
		} catch (JsonGenerationException e) {
			logger.info(e.getMessage());
		} catch (JsonMappingException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return null;
	}
	
	public void setUserOnile(User user) {
		user.setOnline(true);
	}
	
	public void setUserOffline(User user) {
		user.setOnline(false);
	}
	
	public boolean isFriend(User user, User friendUser) throws UserNotFoundException {
		if(user != null && !user.getFriends().isEmpty()) {
			return user.getFriends().contains(friendUser);
		}
		return false;
	}
	
}
