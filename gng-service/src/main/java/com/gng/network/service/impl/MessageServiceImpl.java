package com.gng.network.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.constants.WebConstants;
import com.gng.network.dao.MessageDao;
import com.gng.network.enities.User;
import com.gng.network.exceptions.MessageNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.MessageHelper;
import com.gng.network.json.response.Message;
import com.gng.network.service.MessageService;
import com.gng.network.service.UserService;

@Component("messageService")
public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Inject
	MessageDao messageDao;
	
	@Inject
	UserService userService;
	
	@Inject
	MessageHelper messageHelper;
	
	@Inject
	ObjectMapper mapper;
	
	@Transactional
	public void addMessage(Message message) throws UserNotFoundException {
		User from = userService.findUserById(message.getAuthor());
		User to = userService.findUserById(message.getReceiver());
		if(from == null || to == null) {
			throw new UsernameNotFoundException("user not found");
		}
		message.setAuthorFullname(from.getFullname());
		messageDao.addMessage(from, to, message.getMessage(), System.currentTimeMillis());
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void removeMessage(int messageId) {
		logger.info("remove messages with id", messageId);
		messageDao.removeMessage(messageId);
	}
	
	@Transactional
	public String getJsonMessages(Integer userFromId, Integer userToId, Long millies) throws UserNotFoundException, MessageNotFoundException {
		logger.info("get messages for user", userFromId);
		try {
			User userFrom = userService.findUserById(userFromId);
			User userTo = userService.findUserById(userToId);
			return getJsonMessages(userFrom, userTo, millies);
		} catch(UserNotFoundException e) {
			logger.info(e.getMessage());
			throw new UserNotFoundException(e.getMessage());
		}
	}
	
	private String getJsonMessages(User userFrom, User userTo, Long millies) throws MessageNotFoundException {
		if(millies == -1) {
			return emptyJson();
		}
		Long messageMillies = maxMessageMillies(userFrom, userTo, millies);
		if(messageMillies == null) {
			return emptyJson();
		}
		List<com.gng.network.enities.Message> messagesUserFrom = messageDao.getMessages(userFrom, userTo, messageMillies - WebConstants.TWENTYFOUR_HOURS, messageMillies);
		List<com.gng.network.enities.Message> messagesUserTo = messageDao.getMessages(userTo, userFrom, messageMillies - WebConstants.TWENTYFOUR_HOURS, messageMillies);
		ArrayList<com.gng.network.enities.Message> messages = new ArrayList<com.gng.network.enities.Message>();
		messages.addAll(messagesUserFrom);
		messages.addAll(messagesUserTo);
		while(messages.size() < WebConstants.MESSAGE_WINDOW_AMOUNT) {
			messageMillies = maxMessageMillies(userFrom, userTo, messageMillies);
			if(messageMillies == null) {
				break;
			} 
			messagesUserFrom = messageDao.getMessages(userFrom, userTo, messageMillies - WebConstants.TWENTYFOUR_HOURS, messageMillies);
			messagesUserTo = messageDao.getMessages(userTo, userFrom, messageMillies - WebConstants.TWENTYFOUR_HOURS, messageMillies);
			messages.addAll(messagesUserFrom);
			messages.addAll(messagesUserTo);
		}
		Collections.sort(messages, new Comparator<com.gng.network.enities.Message>() {
			public int compare(com.gng.network.enities.Message m1, com.gng.network.enities.Message m2) {
				if(m1.getTime() > m2.getTime()) {
					return 1;
				}
				if(m2.getTime() > m1.getTime()) {
					return -1;
				}
				return 0;
			}
		});
		List<com.gng.network.json.response.Message> convertedMessages = messageHelper.convertMessagesToJsonMessages(messages);
		String jsonMessages = messageHelper.convertMessagesToJsonString(convertedMessages);
		return messageHelper.constructJsonResponse(jsonMessages, messageMillies != null ? messageMillies : -1 );
		
	}

	private Long maxMessageMillies(User userFrom, User userTo, Long messageMillies) {
		if(messageMillies == 0) {
			Long tempMessageMillies1 = messageDao.getMessageMaxTime(userFrom, userTo);
			Long tempMessageMillies2 = messageDao.getMessageMaxTime(userTo, userFrom);
			messageMillies = compareMillies(tempMessageMillies1, tempMessageMillies2, messageMillies);
		} else {
			Long tempMessageMillies1 = messageDao.getMessageMaxTimeLessThan(userFrom, userTo, messageMillies - WebConstants.TWENTYFOUR_HOURS);
			Long tempMessageMillies2 = messageDao.getMessageMaxTimeLessThan(userTo, userFrom, messageMillies - WebConstants.TWENTYFOUR_HOURS);
			messageMillies = compareMillies(tempMessageMillies1, tempMessageMillies2, messageMillies);
		}
		return messageMillies;
	}

	private Long compareMillies(Long tempMessageMillies1, Long tempMessageMillies2, Long messageMillies) {
		if(tempMessageMillies1 != null && tempMessageMillies2 != null) {
			messageMillies = Math.max(tempMessageMillies1, tempMessageMillies2);
		}
		if(tempMessageMillies1 != null && tempMessageMillies2 == null) {
			messageMillies = tempMessageMillies1;
		}
		if(tempMessageMillies2 != null && tempMessageMillies1 == null) {
			messageMillies = tempMessageMillies2;
		}
		if(tempMessageMillies1 == null && tempMessageMillies2 == null) {
			messageMillies = null;
		}
		return messageMillies;
	}

	private String emptyJson() {
		//empty json
		return "{\"messages\":[], \"messageMillies\":-1}";
	}


}
