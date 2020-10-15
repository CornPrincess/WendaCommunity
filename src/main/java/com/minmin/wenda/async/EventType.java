package com.minmin.wenda.async;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-03
 * Time: 23:48
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);


    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
