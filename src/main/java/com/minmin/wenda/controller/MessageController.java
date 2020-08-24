package com.minmin.wenda.controller;

import com.minmin.wenda.model.HostHolder;
import com.minmin.wenda.model.User;
import com.minmin.wenda.service.MessageService;
import com.minmin.wenda.service.UserService;
import com.minmin.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-08-24
 * Time: 22:23
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/msg/addMessage"}, method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return WendaUtil.getJSONString(999, "Not login");
            }

            User user = userService.selectByName(toName);
        } catch (Exception e) {
            logger.error("Send Message Error: " + e.getMessage());
        }
        return null;
    }
}
