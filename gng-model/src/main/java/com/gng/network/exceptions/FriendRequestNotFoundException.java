package com.gng.network.exceptions;

public class FriendRequestNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public FriendRequestNotFoundException() {}
	
	public FriendRequestNotFoundException(String message) {
		super(message);
	}
}
