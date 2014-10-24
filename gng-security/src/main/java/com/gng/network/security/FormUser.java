package com.gng.network.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.gng.network.enities.FriendRequest;


@SuppressWarnings("serial")
public class FormUser extends User {
	
	private List<com.gng.network.enities.User> friends;
	private Integer userId;
	private String fullname;
	private List<FriendRequest> friendRequests;

	public FormUser(Integer userId, String username, String password, List<FriendRequest> friendRequests,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.userId = userId;
		this.setFriendRequests(friendRequests);
	}
	
	public List<com.gng.network.enities.User> getFriends() {
		return friends;
	}

	public void setFriends(List<com.gng.network.enities.User> friends) {
		this.friends = friends;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(List<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}
	
	

}
