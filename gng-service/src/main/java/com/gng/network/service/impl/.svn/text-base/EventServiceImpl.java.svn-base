package com.gng.network.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.dao.EventDao;
import com.gng.network.enities.Event;
import com.gng.network.enities.User;
import com.gng.network.enums.EventType;
import com.gng.network.exceptions.EventNotFoundException;
import com.gng.network.exceptions.NullEventIdException;
import com.gng.network.exceptions.NullUserIdException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.service.EventService;
import com.gng.network.service.UserService;

@Component("eventService")
public class EventServiceImpl implements EventService {

	@Inject
	private EventDao dao;
	
	@Inject
	private MessageSource messageSource;
	
	@Inject
	private UserService userService;
	
	public Event findEvent(Integer eventId) throws NullEventIdException, EventNotFoundException {
		if(eventId == null) {
			throw new NullEventIdException("Event Id cannot be null");
		}
		Event event = dao.findEvent(eventId);
		if(event == null) {
			throw new EventNotFoundException("Event with specified Id: " + eventId + " was not found");
		}
		return event;
	}

	@Transactional
	public void removeEvent(Integer eventId) throws NullEventIdException {
		if(eventId == null) {
			throw new NullEventIdException("Event Id cannot be null");
		}
		dao.removeEvent(eventId);
		
	}

	@Transactional
	public void updateEvent(Event event) {
		dao.updateEvent(event);
	}

	public Integer createEvent(String eType, Integer userFromId, Integer userToId) throws UserNotFoundException {
		EventType eventType = EventType.valueOf(eType);
		User from = userService.findUserById(userFromId);
		User to = userService.findUserById(userToId);
		String eventMessage = "";
		switch (eventType) {
		case FRIEND_REQUEST:
			eventMessage = messageSource.getMessage("event.type.friend.request", new Object []{from.getFullname()}, null);
			break;
		case PHOTO_TAG:
			eventMessage = messageSource.getMessage("event.type.photo.tag", new Object []{from.getFullname()}, null);
			break;
		case INVITATION:
			eventMessage = messageSource.getMessage("event.type.invitation", new Object []{from.getFullname()}, null);
			break;
		}
		dao.createEvent(eventType, eventMessage, from, to);
		return null;
	}

	public List<Event> getActiveEvents(Integer userToId) throws UserNotFoundException, NullUserIdException {
		if(userToId == null) {
			throw new NullUserIdException("User Id cannot be null");
		}
		User to = userService.findUserById(userToId);
		return dao.getActiveEvents(to);
	}

	public List<Event> getPassiveEvents(Integer userToId) throws UserNotFoundException, NullUserIdException {
		if(userToId == null) {
			throw new NullUserIdException("User Id cannot be null");
		}
		User to = userService.findUserById(userToId);
		return dao.getPassiveEvents(to);
	}


}
