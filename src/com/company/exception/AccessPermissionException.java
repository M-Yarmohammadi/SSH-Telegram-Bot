package com.company.exception;

import com.company.api.core.Bot;
import com.company.api.entity.Message;
import com.company.api.requestObject.RequestSendMessage;

import java.io.IOException;

/**
 * Created by yarmohammadi on 2/10/16.
 */
public class AccessPermissionException extends RuntimeException {
    public AccessPermissionException() {
        super();
    }

    public AccessPermissionException(String message) {
        super(message);
    }

    public AccessPermissionException(Message replyToMessage) throws IOException {
        super();
        RequestSendMessage requestSendMessage = new RequestSendMessage(replyToMessage.getChat(), "Sorry, you not access to this server.");
        requestSendMessage.setReplyToMessageId(replyToMessage.getMessageId());
        Bot.getInstance().sendMessage(requestSendMessage);
    }
}
