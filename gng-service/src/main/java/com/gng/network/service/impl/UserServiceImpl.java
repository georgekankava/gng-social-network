package com.gng.network.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.atmosphere.cpr.AtmosphereResource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.dao.UserDao;
import com.gng.network.enities.Image;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordNotMatchException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.json.response.UsersResponseJson;
import com.gng.network.service.UserService;
import com.gng.network.singletones.AtmosphereConnectionUuids;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;
	
	@Inject
	private UserHelper helper;
	
	@Inject
	private MessageSource messageSource;
	
	@Transactional
	public void persistUser(User user) {
		userDao.persistUser(user);
	}

	@Transactional
	public User loginUser(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			throw new UserNotFoundException("User " + user + " not found");
		}
		if (!user.getPassword().equals(password)) {
			throw new PasswordNotMatchException("");
		}
		return user;
	}

	public List<User> findUsersByFirstname(String firstname) {
		return userDao.findUsersByFirstname(firstname);
	}

	public List<User> findUsersByLastname(String lastname) {
		return userDao.findUsersByLastname(lastname);
	}

	@Transactional
	public User findUserByUsername(String username) throws UserNotFoundException {
		User user = userDao.findUserByUsername(username);
		if(user == null) {
			throw new UserNotFoundException("user not found");
		}
		return user;
	}
	
	@Transactional
	public String searchUsersBySearchString(String searchString) {
		List<User> users = userDao.searchUsersBySearchString(searchString);
		List<UsersResponseJson> convertedUsers = helper.convertUsersToJsonUsers(users);
		return helper.convertUsersToJsonString(convertedUsers);
	}

	public List<User> findUserByFullname(String fullname) {
		return userDao.findUserByFullname(fullname);
	}

	public User findUserById(Integer userId) throws UserNotFoundException {
		User user = userDao.findUserById(userId);
		if(user == null) {
			throw new UserNotFoundException(messageSource.getMessage("user.not.found.exception.message", new Object [] {userId}, null));
		}
		return user;
	}
	
	@Transactional
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<String> getUsersFriendUsernames(User user) {
		User mergedUser = userDao.mergeUser(user);
		List<String> usernames = new ArrayList<String>();
		for(User friend: mergedUser.getFriends()) {
			usernames.add(friend.getUsername());
		}
		return usernames;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Integer> getUsersFriendUserIds(User user) {
		User mergedUser = userDao.mergeUser(user);
		List<Integer> userIds = new ArrayList<Integer>();
		for(User friend: mergedUser.getFriends()) {
			userIds.add(friend.getId());
		}
		return userIds;
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<User> getUsersFriends(User user) {
		User mergedUser = userDao.mergeUser(user);
		return new ArrayList<User>(mergedUser.getFriends());
	}
	
	@Transactional
	public List<User> updateUsersOnlineStatuses(List<User> users) {
		for(User user : users) {
			AtmosphereResource atmosphereResource = AtmosphereConnectionUuids.getInstance().getResource(user.getId());
			if(atmosphereResource != null) {
				if(atmosphereResource.isCancelled()) {
					helper.setUserOffline(user);
				} else {
					helper.setUserOnile(user);
				}
			}
		}
		return users;
	}
	
	@Transactional
	public void addImage(Image img) {
		userDao.persistImage(img);
	}

	@Override
	public Image findUserProfileImage(Integer userId) {
		return userDao.findUserProfileImage(userId);
	}
	
	@Override
	public List<Image> getUserImages(Integer userId) {
		return userDao.getUserImagesById(userId);
		
	}
	
	@Override
	public Image findUserImageByImageId(Integer imageId) {
		Image image = userDao.findUserImageByImageId(imageId);
		return image;
	}
}
