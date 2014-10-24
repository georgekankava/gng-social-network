package com.gng.network.enities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.gng.network.enums.EventType;

@Entity
@Table(name="Event")
@NamedQueries({
	@NamedQuery(name="Event.getEvents", query="FROM Event e WHERE e.activeEvent = :isActive and e.to = :to")
})
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private EventType eType;
	private String eventMessage;
	private Date eventDate;
	private boolean activeEvent;
	@ManyToOne
	private User to;
	@ManyToOne 
	private User from;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EventType geteType() {
		return eType;
	}
	public void seteType(EventType eType) {
		this.eType = eType;
	}
	public String getEventMessage() {
		return eventMessage;
	}
	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public boolean isActiveEvent() {
		return activeEvent;
	}
	public void setActiveEvent(boolean activeEvent) {
		this.activeEvent = activeEvent;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}

	
}
