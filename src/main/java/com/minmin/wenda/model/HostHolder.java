package com.minmin.wenda.model;

import org.springframework.stereotype.Component;

/**
 * @author corn
 * @version 1.0.0
 */

@Component
public class HostHolder {
    // TODO 学习什么是ThreadLocal
    // 线程本地变量，底层可以理解为Map<Thread, user>
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
