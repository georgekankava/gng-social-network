package com.gng.network.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by georgekankava on 11.05.17.
 */
@Getter
@Setter
public class ApplicationException extends Exception {

    private String keyCode;

    private Throwable rootCause;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, String keyCode) {
        super(message);
        this.keyCode = keyCode;
    }

    public ApplicationException(String message, String keyCode, Throwable th) {
        super(message, th);
        this.keyCode = keyCode;
        this.rootCause = th;
    }

}
