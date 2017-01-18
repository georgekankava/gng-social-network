package com.gng.network.dao;

import java.util.List;

import com.gng.network.enities.Image;
import com.gng.network.enities.Message;
import com.gng.network.enities.User;
import com.gng.network.exceptions.DaoException;

public interface UserDao {
	void persistUser(User user);
	List<User> findUsersByFirstname(String firstname);
	List<User> findUsersByLastname(String lastname);
	User findUserByUsername(String username);
	List<User>findUserByFullname(String fullname);
	User findUserById(Integer userId);
	void updateUser(User user);
	User mergeUser(User user);
	List<User> searchUsersBySearchString(String searchString);
	void persistImage(Image img);
	Image findUserProfileImage(Integer userId);
	List<Image> getUserImagesById(Integer userId);
	Image findUserImageByImageId(Integer imageId);
	List<User> loadUserMessageList(Integer userId) throws DaoException;
}
