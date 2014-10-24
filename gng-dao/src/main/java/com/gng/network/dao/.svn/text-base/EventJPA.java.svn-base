package com.gng.network.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.enities.Event;
import com.gng.network.enities.User;
import com.gng.network.enums.EventType;

@Repository("eventDao")
public class EventJPA implements EventDao {

	@PersistenceContext
	private EntityManager em;

	public Event findEvent(Integer eventId) {
		Event event = em.find(Event.class, eventId);
		return event;
	}

	@Transactional
	public void removeEvent(Integer eventId) {
		Event event = em.find(Event.class, eventId);
		em.remove(event);
	}

	@Transactional
	public void updateEvent(Event event) {
		em.merge(event);
		em.flush();
	}

	@Transactional
	public Integer createEvent(EventType eType, String eventMessage, User from, User to) {
		Event event = new Event();
		event.setActiveEvent(true);
		event.seteType(eType);
		event.setEventDate(new Date());
		event.setEventMessage(eventMessage);
		event.setFrom(from);
		event.setTo(to);
		em.persist(event);
		em.refresh(event);
		return event.getId();
	}

 	public List<Event> getActiveEvents(User to) {
		return getEvents(to, true);
	}

	public List<Event> getPassiveEvents(User to) {
		return getEvents(to, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getEvents(User to, boolean isActive) {
		return (List<Event>)em.createNamedQuery("Event.getEvents").setParameter("to", to).setParameter("isActive", isActive).getResultList();
	}

}
