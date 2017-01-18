package com.gng.network.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by georgekankava on 08.01.17.
 */
@Getter
@Setter
public class ServiceException extends Exception {

    private String keyCode;

    private ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String keyCode) {
        super(message);
        this.keyCode = keyCode;
    }

    public ServiceException(String message, String keyCode, Throwable th) {
        super(message, th);
        this.keyCode = keyCode;
    }

}
