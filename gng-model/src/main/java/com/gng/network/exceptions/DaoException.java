package com.gng.network.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by georgekankava on 08.01.17.
 */
@Getter
@Setter
public class DaoException extends Exception {

    private String keyCode;

    private DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, String keyCode) {
        super(message);
        this.keyCode = keyCode;
    }

    public DaoException(String message, String keyCode, Throwable th) {
        super(message, th);
        this.keyCode = keyCode;
    }

}