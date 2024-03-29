package com.minmin.wenda.service;

import com.minmin.wenda.dao.LoginTicketDAO;
import com.minmin.wenda.dao.UserDAO;
import com.minmin.wenda.model.LoginTicket;
import com.minmin.wenda.model.User;
import com.minmin.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author corn
 * @version 1.0.0
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;


    /*
        select user by id service
     */
    public User getUser(int id){
        return userDAO.selectById(id);
    }

    public User selectByName(String name) {
        return userDAO.selectByName(name);
    }

    /*
        register user service
     */
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();

        if(StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return  map;
        }

        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);

        // set salt
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));

        // add user
        userDAO.addUser(user);

        // add ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    /*
        login user service
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();

        if(StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return  map;
        }

        if(StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if(user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        // 检验密码
        if(! WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        // add ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }

    /*
        logout service
     */

    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket, 1);
    }


    /*
        add ticket
     */
    public  String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();

        loginTicket.setUserId(userId);
        Date expireDate = new Date();
        expireDate.setTime(3600*24*1000 + expireDate.getTime());
        loginTicket.setExpired(expireDate);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));

        loginTicketDAO.addTicket(loginTicket);

        return loginTicket.getTicket();
    }
}
