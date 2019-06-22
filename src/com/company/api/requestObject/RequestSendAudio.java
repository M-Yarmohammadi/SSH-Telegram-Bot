package com.company.api.requestObject;

import com.company.api.entity.*;

/**
 * Created by yarmohammadi on 2/6/2016 AD.
 */
public class RequestSendAudio {
    private Chat chat;
    private Audio audio;

    //access these field with getAudio and setAudio methods;
    private InputFile inputFile;
    private String fileId;

    private int duration;
    private String performer;
    private String title;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;

    public RequestSendAudio(){}

    public RequestSendAudio(Chat chat, Object audio){
        this.chat = chat;
        setAudio(audio);
    }

    public RequestSendAudio(Object chatId, Object audio){
        this.chat = new Chat(chatId);
        setAudio(audio);
    }

    public Object getAudio() {
        if (this.inputFile != null) {
            return this.inputFile;
        } else {
            return fileId;
        }
    }

    public void setAudio(Object audio) {
        if (audio instanceof InputFile) {
            this.inputFile = (InputFile) audio;
        } else if (audio instanceof String) {
            this.fileId = String.valueOf(audio);
        }
    }

    public void setReplyMarkup(Object replyMarkup) {
        if (replyMarkup instanceof ReplyKeyboardMarkup) {
            this.replyKeyboardMarkup = (ReplyKeyboardMarkup) replyMarkup;
        } else if (replyMarkup instanceof ReplyKeyboardHide) {
            this.replyKeyboardHide = (ReplyKeyboardHide) replyMarkup;
        } else if (replyMarkup instanceof ForceReply) {
            this.forceReply = (ForceReply) replyMarkup;
        }
    }

    public Object getReplyMarkup(){
        if (this.replyKeyboardMarkup != null){
            return this.replyKeyboardMarkup;
        }if (this.replyKeyboardHide != null){
            return this.replyKeyboardHide;
        }else {
            return this.forceReply;
        }
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }
}
