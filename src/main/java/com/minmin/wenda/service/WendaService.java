package com.minmin.wenda.service;

import org.springframework.stereotype.Service;

/**
 * @author corn
 * @version 1.0.0
 */

@Service
public class WendaService {
    public String getMessage(int userId) {
        return "hello" + String.valueOf(userId);
    }
}
