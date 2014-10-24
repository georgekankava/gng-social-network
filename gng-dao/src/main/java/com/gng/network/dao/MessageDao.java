package com.gng.network.dao;

import java.util.List;

import com.gng.network.enities.Message;
import com.gng.network.enities.User;


public interface MessageDao {
	void addMessage(User userFrom, User userTo, String message, long time);
	void removeMessage(int messageId);
	List<Message> getMessages(User userFrom, User userTo, Long fromMillies, Long toMillies);
	Long getMessageMaxTime(User userFrom, User userTo);
	Long getMessageMaxTimeLessThan(User userFrom, User userTo, Long messageMillies);
}
