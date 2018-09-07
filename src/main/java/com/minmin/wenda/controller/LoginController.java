package com.minmin.wenda.controller;

import com.minmin.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author corn
 * @version 1.0.0
 */

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    UserService userService;


    // register controller
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password) {

        try {
            Map<String, String> map =  userService.register(username, password);
            if(map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return  "redirect:/";

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return  "login";
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme ) {


        try {
            Map<String, String> map =  userService.register(username, password);
            if(map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return  "redirect:/";

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return  "login";
        }




        return "login";
    }
}
