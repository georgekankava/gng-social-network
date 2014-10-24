package com.gng.network.exceptions;

public class EventNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventNotFoundException() {
		super();
	}

	public EventNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventNotFoundException(String message) {
		super(message);
	}

	public EventNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
