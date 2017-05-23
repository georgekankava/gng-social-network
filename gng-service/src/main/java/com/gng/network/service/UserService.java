package com.gng.network.service;

import java.util.List;

import com.gng.network.enities.Image;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordDoNotMatchException;
import com.gng.network.exceptions.ServiceException;
import com.gng.network.exceptions.UserNotFoundException;

public interface UserService {
	void persistUser(User user);
	User loginUser(String username, String password) throws UserNotFoundException, PasswordDoNotMatchException;
	List<User> findUsersByFirstname(String firstname);
	List<User> findUsersByLastname(String lastname);
	User findUserByUsername(String username) throws UserNotFoundException;
	List<User> findUserByFullname(String fullname);
	User findUserById(Integer userId) throws UserNotFoundException;
	void updateUser(User user);
	List<String> getUsersFriendUsernames(User user);
	List<Integer> getUsersFriendUserIds(User user);
	List<User> getUsersFriends(User user);
	String searchUsersBySearchString(String searchString);
	List<User> updateUsersOnlineStatuses(List<User> users);
	void addImage(Image img);
	Image findUserProfileImage(Integer userId);
	List<Image> getUserImages(Integer userId);
	Image findUserImageByImageId(Integer imageId);
	List<User> getUserMessageList(Integer userId) throws ServiceException;
}
