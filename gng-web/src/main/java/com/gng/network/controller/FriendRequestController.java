package com.gng.network.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.User;
import com.gng.network.exceptions.FriendRequestNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.service.FriendRequestService;

@Controller
public class FriendRequestController {
	
	@Inject
	FriendRequestService requestService;
	
	@Inject
    MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(FriendRequestController.class);
	
	@RequestMapping(value = "/send-friend-request.ajax")
	public void sendFriendRequest(@RequestParam Integer fromId, @RequestParam Integer toId) throws IOException {
		try {
			requestService.sendFriendRequest(fromId, toId);
		} catch (UserNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/accept-friend-request.ajax")
	public void addFriend(java.io.Writer writer, @RequestParam("friendRequestId") Integer friendRequestId) throws IOException {
		try {
			FriendRequest fr = requestService.acceptFriendRequest(friendRequestId);
			User user = fr.getUserFrom();
			String responseJson = messageSource.getMessage("accept.friend.request.response", new Object[]{user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getFullname()}, null);
			writer.write(responseJson);
		} catch (FriendRequestNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/deny-friend-request.ajax")
	public void denyFriendRequest(@RequestParam("requestId") Integer friendRequestId) throws IOException {
		requestService.removeFriendRequest(friendRequestId);
	}
}
