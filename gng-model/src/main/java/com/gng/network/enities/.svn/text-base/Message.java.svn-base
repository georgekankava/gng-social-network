package com.gng.network.enities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Message.getMessageMaxMillies", query = "SELECT MAX(message.time) FROM Message message WHERE message.userFrom = :userFrom AND message.userTo = :userTo"),
	@NamedQuery(name = "Message.getMessageMaxMilliesLessThen", query = "SELECT MAX(message.time) FROM Message message WHERE message.time < :time AND message.userFrom = :userFrom AND message.userTo = :userTo"),
	@NamedQuery(name = "Message.getMessages", query = "FROM Message message WHERE message.userTo = :userTo AND message.userFrom = :userFrom AND message.time BETWEEN :fromTime AND :toTime")
})
public class Message {
	
	public Message() {}
	
	public Message(User userFrom, User userTo, String message, long time) {
		this.userFrom = userFrom;
		this.userTo = userTo;
		this.message = message;
		this.time = time;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "user_to")
	private User userTo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "user_from")
	private User userFrom;
	
	private String message;
	
	private long time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "{ \"message\" : \"" + message + "\", \"userFrom\" : \"" + userFrom + "\" , \"userTo\" : \"" + userTo + "\", \"time\" : " + new Date().getTime() + "}";
	}
	
	
}
