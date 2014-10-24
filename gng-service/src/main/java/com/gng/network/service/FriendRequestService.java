package com.gng.network.service;

import java.util.List;

import com.gng.network.enities.FriendRequest;
import com.gng.network.exceptions.FriendRequestNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;

public interface FriendRequestService {
	FriendRequest acceptFriendRequest(int friendRequestId) throws FriendRequestNotFoundException;
	void sendFriendRequest(Integer fromId, Integer toId) throws UserNotFoundException;
	void removeFriendRequest(int requestId);
	List<FriendRequest> getPendingRequests(Integer userId);
}
