package com.company.api.core;

import com.company.api.entity.File;
import com.company.api.entity.Message;
import com.company.api.entity.User;
import com.company.api.entity.UserProfilePhoto;
import com.company.api.exception.*;
import com.company.api.json.JSONObject;
import com.company.api.net.SSLConnection;
import com.company.api.requestObject.*;
import com.company.configuration.Config;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Bot {
    private String id;
    private String username;
    private String token;

    private static Bot instance = null;

    private static String apiUrl = "https://api.telegram.org/bot";

    private Bot(String token) {
        this.token = token;
    }

    public static Bot getInstance() throws IOException {
        if (instance == null) {
            instance = new Bot(Config.getInstance().getToken());
        }

        return instance;
    }

    public User getMe() throws IOException {
        String getMeUrl = apiUrl + this.token + "/getMe";
        SSLConnection sslConnection = new SSLConnection(getMeUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        User user = new User();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            user = gson.fromJson(jsonResponse.get("result").toString(), User.class);
        } else {
            //TODO: write exception for error in response from telegram;
        }

        return user;
    }

    public List<Message> getUpdates(RequestGetUpdate requestGetUpdate) throws IOException {
        String updateUrl = apiUrl + this.token + "/getUpdates?";

        if (requestGetUpdate.getOffset() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "offset=" + requestGetUpdate.getOffset();
        }
        if (requestGetUpdate.getLimit() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "limit=" + requestGetUpdate.getLimit();
        }
        if (requestGetUpdate.getTimeout() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "timeout=" + requestGetUpdate.getTimeout();
        }

        SSLConnection sslConnection = new SSLConnection(updateUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        List<Message> listOfAllMessage = new ArrayList<Message>();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();

            for (int counter = 0; counter < jsonResponse.getJSONArray("result").length(); counter++) {
                Message message = gson.fromJson(jsonResponse.getJSONArray("result").getJSONObject(counter).get("message").toString()
                        , Message.class);
                message.setUpdateId((Integer) jsonResponse.getJSONArray("result").getJSONObject(counter).get("update_id"));
                listOfAllMessage.add(message);
            }
        } else {
            //TODO: write telegram error message in exception;
            throw new GetUpdateException();
        }

        return listOfAllMessage;
    }

    public void forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException {

        String chatId;
        if (requestForwardMessage.getChat().getId() != 0) {
            chatId = String.valueOf(requestForwardMessage.getChat().getId());
        } else if (requestForwardMessage.getChat().getUsername() != null) {
            chatId = requestForwardMessage.getChat().getUsername();
        } else {
            throw new ForwardMessageException("Chat id or chat username is null");
        }

        String forwardUrl = apiUrl + this.token + "/forwardMessage?chat_id=" + chatId
                + "&from_chat_id=" + requestForwardMessage.getMessage().getChat().getId()
                + "&message_id=" + requestForwardMessage.getMessage().getMessageId();
        SSLConnection sslConnection = new SSLConnection(forwardUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new ForwardMessageException(e.getMessage());
        }

    }

    public void sendMessage(RequestSendMessage requestSendMessage) throws IOException {
        String chatId;
        if (requestSendMessage.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendMessage.getChat().getId());
        } else if (requestSendMessage.getChat().getUsername() != null) {
            chatId = requestSendMessage.getChat().getUsername();
        } else {
            throw new SendMessageException("Chat id or chat username is null");
        }

        String sendMessageUrl = apiUrl + this.token + "/sendMessage?chat_id=" + chatId
                + "&text=" + URLEncoder.encode(requestSendMessage.getText(), "UTF-8");

        if (requestSendMessage.getParseMode() != null) {
            sendMessageUrl = sendMessageUrl + "&parse_mode=" + requestSendMessage.getParseMode();
        }
        if (requestSendMessage.getReplyToMessageId() != 0) {
            sendMessageUrl = sendMessageUrl + "&reply_to_message_id=" + requestSendMessage.getReplyToMessageId();
        }
        if (requestSendMessage.getDisableWebPagePreview()) {
            sendMessageUrl = sendMessageUrl + "&disable_web_page_preview=" + requestSendMessage.getDisableWebPagePreview();
        }
        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendMessageUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendMessageException(e.getMessage());
        }
    }

    public void sendLocation(RequestSendLocation requestSendLocation) {
        String chatId;
        if (requestSendLocation.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendLocation.getChat().getId());
        } else if (requestSendLocation.getChat().getUsername() != null) {
            chatId = requestSendLocation.getChat().getUsername();
        } else {
            throw new SendLocationException("Chat id or chat username is null");
        }

        String sendLocationUrl = apiUrl + this.token + "/sendLocation?chat_id=" + chatId
                + "&latitude=" + requestSendLocation.getLocation().getLatitude()
                + "&longitude=" + requestSendLocation.getLocation().getLongitude();

        if (requestSendLocation.getReplyToMessageId() != 0) {
            sendLocationUrl = sendLocationUrl + "&reply_to_message_id=" + requestSendLocation.getReplyToMessageId();
        }
        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendLocationUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendLocationException(e.getMessage());
        }
    }

    public void sendChatAction(RequestSendChatAction requestSendChatAction) {
        String chatId;
        if (requestSendChatAction.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendChatAction.getChat().getId());
        } else if (requestSendChatAction.getChat().getUsername() != null) {
            chatId = requestSendChatAction.getChat().getUsername();
        } else {
            throw new SendChatActionException("Chat id or chat username is null");
        }

        String sendChatActionUrl = apiUrl + this.token + "/sendChatAction?chat_id=" + chatId
                + "&action=" + requestSendChatAction.getAction();

        SSLConnection sslConnection = new SSLConnection(sendChatActionUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    public UserProfilePhoto getUserProfilePhotos(RequestGetUserProfilePhotos requestGetUserProfilePhotos) throws IOException {
        String getUserProfilePhotoUrl = apiUrl + this.token + "/getUserProfilePhotos?user_id="
                + requestGetUserProfilePhotos.getUser().getId();

        if (requestGetUserProfilePhotos.getLimit() != 0) {
            getUserProfilePhotoUrl = getUserProfilePhotoUrl + "&limit=" + requestGetUserProfilePhotos.getLimit();
        }
        if (requestGetUserProfilePhotos.getOffset() != 0) {
            getUserProfilePhotoUrl = getUserProfilePhotoUrl + "&offset=" + requestGetUserProfilePhotos.getOffset();
        }

        SSLConnection sslConnection = new SSLConnection(getUserProfilePhotoUrl);

        JSONObject jsonResponse = sslConnection.getSSLConnection();

        UserProfilePhoto userProfilePhoto;
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            userProfilePhoto = gson.fromJson(jsonResponse.get("result").toString(), UserProfilePhoto.class);
        } else {
            //TODO: write telegram error message exception;
            throw new GetUserProfilePhotosException();
        }

        return userProfilePhoto;
    }

    public File getFile(RequestGetFile requestGetFile) throws IOException {
        String getFileUrl = apiUrl + this.token + "/getfile?file_id=" + requestGetFile.getFile().getFile_id();

        SSLConnection sslConnection = new SSLConnection(getFileUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        File file;
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            file = gson.fromJson(jsonResponse.get("result").toString(), File.class);
        } else {
            //TODO: write telegram error message exception;
            throw new GetFileException();
        }

        return file;
    }

    public void sendAudio(RequestSendAudio requestSendAudio) {

    }

    //TODO: other method for bot telegram entity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
