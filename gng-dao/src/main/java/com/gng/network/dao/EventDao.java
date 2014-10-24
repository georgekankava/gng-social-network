package com.gng.network.dao;

import java.util.List;

import com.gng.network.enities.Event;
import com.gng.network.enities.User;
import com.gng.network.enums.EventType;

public interface EventDao {
	Event findEvent(Integer eventId);
	void removeEvent(Integer eventId);
	void updateEvent(Event event);
	Integer createEvent(EventType eType, String eventMessage, User from, User to);
	List<Event> getActiveEvents(User to);
	List<Event> getPassiveEvents(User to);
	List<Event> getEvents(User to, boolean isActive);
}
