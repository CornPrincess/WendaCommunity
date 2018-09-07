package com.minmin.wenda.service;

import com.minmin.wenda.dao.UserDAO;
import com.minmin.wenda.model.User;
import com.minmin.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author corn
 * @version 1.0.0
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    /*
        select user by id service
     */
    public User getUser(int id){
        return userDAO.selectById(id);
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
        return map;
    }


    public Map<String, String> login(String username, String password) {
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
        if(user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        // 检验密码
        if(! WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        return map;
    }
}
