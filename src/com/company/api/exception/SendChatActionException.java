package com.company.api.exception;

/**
 * Created by yarmohammadi on 2/5/2016 AD.
 */
public class SendChatActionException extends RuntimeException {

    public SendChatActionException() {
        super();
    }

    public SendChatActionException(String message) {
        super(message);
    }
}
