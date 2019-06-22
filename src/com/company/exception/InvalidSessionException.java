package com.company.exception;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestSendMessage;

import java.io.IOException;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException(){super();}

    public InvalidSessionException(String message){super(message);}

    public InvalidSessionException(Message replyToMessage) throws IOException {
        super();
        RequestSendMessage requestSendMessage = new RequestSendMessage(replyToMessage.getChat(), "Sorry, Invalid session for this server." +
                "Please first create session with /startSession command.");
        requestSendMessage.setReplyToMessageId(replyToMessage.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }
}
