package com.gng.network.service;

import java.util.List;

import com.gng.network.enities.Event;
import com.gng.network.exceptions.EventNotFoundException;
import com.gng.network.exceptions.NullEventIdException;
import com.gng.network.exceptions.NullUserIdException;
import com.gng.network.exceptions.UserNotFoundException;

public interface EventService {
	Event findEvent(Integer eventId) throws NullEventIdException, EventNotFoundException;
	void removeEvent(Integer eventId) throws NullEventIdException;
	void updateEvent(Event event);
	Integer createEvent(String eType, Integer userFromId, Integer userToId) throws UserNotFoundException;
	List<Event> getActiveEvents(Integer userToId) throws UserNotFoundException, NullUserIdException;
	List<Event> getPassiveEvents(Integer userToId) throws UserNotFoundException, NullUserIdException;
}
