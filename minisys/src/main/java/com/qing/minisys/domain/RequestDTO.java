package com.qing.minisys.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RequestDTO<T> {

    private String requestTime;
    private String ip;
    private T reqdata;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public T getReqdata() {
        return reqdata;
    }

    public void setReqdata(T reqdata) {
        this.reqdata = reqdata;
    }

    @Override
    public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
