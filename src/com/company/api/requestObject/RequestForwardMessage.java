package com.company.api.requestObject;

import com.company.api.entity.Chat;
import com.company.api.entity.Message;

/**
 * Created by yarmohammadi on 1/23/2016 AD.
 */
public class RequestForwardMessage {
    private Message message;
    private Chat chat;

    public RequestForwardMessage() {
    }

    public RequestForwardMessage(Chat chat, Message message) {
        this.chat = chat;
        this.message = message;
    }

    public RequestForwardMessage(Object chatId, Object fromChatId, int messageId) {
        this.chat = new Chat(chatId);
        this.message = new Message();
        this.message.setChat(new Chat(fromChatId));
        this.message.setMessageId(messageId);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
