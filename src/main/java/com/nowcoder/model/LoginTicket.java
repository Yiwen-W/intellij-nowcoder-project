package com.nowcoder.model;

import javax.xml.crypto.Data;

/**
 * Created by Wang Yiwen on 2017/5/6.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Data expired;
    private int status;
    private String ticket;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Data getExpired() {
        return expired;
    }

    public void setExpired(Data expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }


}
