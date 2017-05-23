package com.gng.network.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PasswordDoNotMatchException extends ApplicationException {
	private PasswordDoNotMatchException() {
	}

	public PasswordDoNotMatchException(String message) {
		super(message);
	}

	public PasswordDoNotMatchException(String message, String keyCode) {
		super(message, keyCode);
	}

	public PasswordDoNotMatchException(String message, String keyCode, Throwable th) {
		super(message, keyCode, th);
	}
}
