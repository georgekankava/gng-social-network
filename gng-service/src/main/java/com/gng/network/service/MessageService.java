package com.gng.network.service;

import java.util.List;

import com.gng.network.enities.User;
import com.gng.network.exceptions.MessageNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.json.response.Message;

public interface MessageService {
	void addMessage(Message message) throws UserNotFoundException;
	void removeMessage(int messageId);
	List<com.gng.network.enities.Message> getMessages(Integer userFromId, Integer userToId, long messageMillies) throws MessageNotFoundException, UserNotFoundException;
	String getJsonMessages(Integer userFromId, Integer userToId, Long fromMillies) throws UserNotFoundException, MessageNotFoundException;
}
