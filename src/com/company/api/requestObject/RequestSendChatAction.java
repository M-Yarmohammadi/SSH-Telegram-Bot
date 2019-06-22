package com.company.api.requestObject;

import com.company.api.entity.Chat;

/**
 * Created by yarmohammadi on 2/5/2016 AD.
 */
public class RequestSendChatAction {
    private Chat chat;

    //TODO: this field can be enum variable;
    private String action;          //typing for text messages, upload_photo for photos, record_video or upload_video for videos,
                                    // record_audio or upload_audio for audio files, upload_document for general files,
                                    // find_location for location data.

    public RequestSendChatAction(){}

    public RequestSendChatAction(Chat chat, String action){
        this.chat = chat;
        this.action = action;
    }

    public RequestSendChatAction(Object chatId, String action){
        this.chat = new Chat(chatId);
        this.action = action;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
