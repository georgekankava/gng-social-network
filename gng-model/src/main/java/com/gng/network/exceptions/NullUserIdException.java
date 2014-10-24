package com.gng.network.exceptions;

public class NullUserIdException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullUserIdException() {
		super();
	}

	public NullUserIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullUserIdException(String message) {
		super(message);
	}

	public NullUserIdException(Throwable cause) {
		super(cause);
	}

}
