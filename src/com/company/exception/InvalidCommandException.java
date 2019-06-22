package com.company.exception;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestSendMessage;

import java.io.IOException;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(){super();}

    public InvalidCommandException(String message){super(message);}

    public InvalidCommandException(Message replyToMessage) throws IOException {
        super();
        RequestSendMessage requestSendMessage = new RequestSendMessage(replyToMessage.getChat(), "Your command is incorrect. see /help.");
        requestSendMessage.setReplyToMessageId(replyToMessage.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }

}
