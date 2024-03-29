package com.minmin.wenda.controller;

import com.minmin.wenda.async.EventModel;
import com.minmin.wenda.async.EventProducer;
import com.minmin.wenda.async.EventType;
import com.minmin.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    EventProducer eventProducer;


    // register controller
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                      @RequestParam(value="next", required = false) String next,
                      HttpServletResponse response){

        try {
            Map<String, String> map =  userService.register(username, password);

            // add cookie
            if(map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");

                // 设置cookie过期时间为5天
                if(rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }

                response.addCookie(cookie);

                // 跳转
                if(StringUtils.isNotBlank(next)) {
                    return  "redirect:" + next;
                }
                return  "redirect:/";

            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return  "login";
        }
    }

    // login controller
    // TODO session共享：一次登录之后就把token信息放在公共的地方，然后服务器都去访问这个地方获取信息，至于怎么存储的可以有各种方法
    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value="next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {


        try {
            Map<String, Object> map =  userService.login(username, password);

            // add cookie
            if(map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                // 设置cookie过期时间为5天
                if(rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);

                // TODO send email to user when ip is abnormal
//                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
//                        .setExt("username", username).setExt("email", "to@qq.com")
//                        .setActorId((int)map.get("userId")));

                // 跳转
                if(StringUtils.isNotBlank(next)) {
                    return  "redirect:" + next;
                }
                return  "redirect:/";

            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return  "login";
        }

    }

    // login and register page controller
    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String regloginPage(Model model, @RequestParam(value="next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    // logout controller
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }



}
