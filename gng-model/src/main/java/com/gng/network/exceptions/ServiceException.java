package com.gng.network.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by georgekankava on 08.01.17.
 */
@Getter
@Setter
public class ServiceException extends ApplicationException {
    private ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String keyCode) {
        super(message, keyCode);
    }

    public ServiceException(String message, String keyCode, Throwable th) {
        super(message, keyCode, th);
    }
}
