package com.gng.network.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.dao.FriendRequestDao;
import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.User;
import com.gng.network.exceptions.FriendRequestNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.service.FriendRequestService;
import com.gng.network.service.UserService;

@Service("friendRequestService")
public class FriendRequestServiceImpl implements FriendRequestService {
	
	@Inject
	private FriendRequestDao friendRequestDao;
	
	@Inject
	private UserService userService;
	
	@Inject
	private DataSource dataSource;
	
	public FriendRequest acceptFriendRequest(int friendRequestId) throws FriendRequestNotFoundException {
		FriendRequest fr = friendRequestDao.getPendingRequest(friendRequestId);
		if (fr == null || fr == null) {
			throw new FriendRequestNotFoundException("Friend Request with Id " + friendRequestId + " not found");
		}
		User friendRequestTo = fr.getUserTo();
		User friendRequestFrom = fr.getUserFrom();
		addFriend(friendRequestTo, friendRequestFrom);
		addFriend(friendRequestFrom, friendRequestTo);
		removeFriendRequest(friendRequestId);
		return fr;
	}

	private JdbcTemplate addFriend(User friendRequestTo, User friendRequestFrom) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("INSERT INTO FRIENDS_TABLE (USER_ID, FRIEND_USER_ID) VALUES(?,?)",
				new Object[] {friendRequestTo.getId(), friendRequestFrom.getId()});
		return jdbcTemplate;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void sendFriendRequest(Integer fromId, Integer toId) throws UserNotFoundException {
		User from = userService.findUserById(fromId);
		User to = userService.findUserById(toId);
		if(from == null) {
			throw new UserNotFoundException("User with id: " + fromId + " not found");
		}
		if(to == null) { 
			throw new UserNotFoundException("to User with id: " + toId + " not found");
		}
		friendRequestDao.addFriendRequest(from, to);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeFriendRequest(int requestId) {
		friendRequestDao.removeFriendRequest(requestId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<FriendRequest> getPendingRequests(Integer userId) {
		return friendRequestDao.getPendingRequests(userId);
	}
}
