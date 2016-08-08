package com.gng.network.exceptions;

/**
 * Created by georgekankava on 8/8/16.
 */
public class EmptyListException extends Exception {

    private String messageKey;

    public EmptyListException(String messageKey) {
        super();
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
