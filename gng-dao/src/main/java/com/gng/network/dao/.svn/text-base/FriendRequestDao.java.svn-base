package com.gng.network.dao;

import java.util.List;

import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;

public interface FriendRequestDao {
	void addFriendRequest(User from, User to) throws UserNotFoundException;
	List<FriendRequest> getPendingRequests(Integer userId);
	FriendRequest getPendingRequest(Integer requestId);
	void removeFriendRequest(Integer friendRequestId);
	
}
