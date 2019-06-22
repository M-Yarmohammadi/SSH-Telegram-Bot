package com.company.api.entity;

public class ForceReply {
    private boolean force_reply;
    private boolean selective;

    public boolean isForceReply() {
        return force_reply;
    }

    public void setForceReply(boolean forceReply) {
        this.force_reply = forceReply;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
