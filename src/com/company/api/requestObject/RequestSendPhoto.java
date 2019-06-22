package com.company.api.requestObject;

import com.company.api.entity.*;

/**
 * Created by yarmohammadi on 2/4/2016 AD.
 */
public class RequestSendPhoto {
    private Chat chat;
    private PhotoSize photo;
    private InputFile newPhoto;
    private String caption;
    private Message replyToMessage;
    private ReplyKeyboardMarkup replyMarkup;
}
