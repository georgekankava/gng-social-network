package com.gng.network.service;

import com.gng.network.exceptions.MessageNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.json.response.Message;

public interface MessageService {
	void addMessage(Message message) throws UserNotFoundException;
	void removeMessage(int messageId);
	String getJsonMessages(Integer userFromId, Integer userToId, Long fromMillies) throws UserNotFoundException, MessageNotFoundException;
}
