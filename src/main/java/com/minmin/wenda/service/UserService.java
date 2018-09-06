package com.minmin.wenda.service;

import com.minmin.wenda.dao.UserDAO;
import com.minmin.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author corn
 * @version 1.0.0
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }
}
