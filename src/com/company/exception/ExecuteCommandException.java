package com.company.exception;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestSendMessage;

import java.io.IOException;

/**
 * Created by yarmohammadi on 2/13/16.
 */
public class ExecuteCommandException extends RuntimeException {
    public ExecuteCommandException(){super();}

    public ExecuteCommandException(String message){super(message);}

    public ExecuteCommandException(Message replyToMessage) throws IOException {
        super();
        RequestSendMessage requestSendMessage = new RequestSendMessage(replyToMessage.getChat(), "Some exception occur.Check your command and permissions and try again.");
        requestSendMessage.setReplyToMessageId(replyToMessage.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }
}
