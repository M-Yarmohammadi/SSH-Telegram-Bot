package com.company.api.requestObject;

import com.company.api.entity.User;

/**
 * Created by yarmohammadi on 2/5/2016 AD.
 */
public class RequestGetUserProfilePhotos {
    private User user;
    private int offset;
    private int limit;

    public RequestGetUserProfilePhotos(){
    }

    public RequestGetUserProfilePhotos(User user){
        this.user = user;
    }

    public RequestGetUserProfilePhotos(int userId){
        this.user = new User(userId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
