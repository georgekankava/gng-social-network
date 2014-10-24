package com.gng.network.enities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "friend_request")
@NamedQueries({
	@NamedQuery(name = "FriendRequest.getPendingRequests", query = "FROM FriendRequest friendRequest WHERE friendRequest.userTo.id = :id"),
	@NamedQuery(name = "FriendRequest.findPendingRequestByUsers", query = "FROM FriendRequest friendRequest WHERE friendRequest.userTo = :userTo AND friendRequest.userFrom = :userFrom")
	})
public class FriendRequest {
	
	public FriendRequest() {
	}
	
	public FriendRequest(User userTo, User userFrom) {
		this.userTo = userTo;
		this.userFrom = userFrom;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_to")
	private User userTo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_from")
	private User userFrom;

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

	@Override
	public String toString() {
		return "FriendRequest [id=" + id + ", userTo=" + userTo + ", userFrom=" + userFrom + "]";
	}
	
	
}
