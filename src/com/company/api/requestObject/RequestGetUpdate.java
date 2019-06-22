package com.company.api.requestObject;

/**
 * Created by yarmohammadi on 2/9/2016 AD.
 */
public class RequestGetUpdate {
    private int offset;
    private int limit;
    private int timeout;

    public RequestGetUpdate(){}

    public RequestGetUpdate(int offset, int limit, int timeout){
        this.offset = offset;
        this.limit = limit;
        this.timeout = timeout;
    }

    public RequestGetUpdate(int offset){
        this.offset = offset;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
