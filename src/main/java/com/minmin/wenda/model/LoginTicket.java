package com.minmin.wenda.model;

import java.util.Date;

/**
 * @author corn
 * @version 1.0.0
 * save the login user info
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status; // 0有效，1无效
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

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
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
