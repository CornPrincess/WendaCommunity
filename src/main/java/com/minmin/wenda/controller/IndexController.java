package com.minmin.wenda.controller;

import com.minmin.wenda.aspect.LogAspect;
import com.minmin.wenda.model.User;
import com.minmin.wenda.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author corn
 * @version 1.0.0
 */

// @Controller
public class IndexController {

    @Autowired
    WendaService wendaService;
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession httpSession) {
        log.info("visit home");

        return "hello minmin" + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "minmin", required = false) String key) {
        return String.format("profile page of %s / %d  type:%d, key:%s", groupId, userId, type, key);
    }


    @RequestMapping(value = "/method7/{id}")
    @ResponseBody
    public String method7(@PathVariable("id") int id) {
        return "method7 with id=" + id;
    }

    @RequestMapping(value = "/method8/{id:[\\d]+}/{name}")
    @ResponseBody
    public String method8(@PathVariable("id") long id, @PathVariable("name") String name) {
        return "method8 with id= " + id + " and name=" + name;
    }


   /* @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod() {
        return "fallback method";
    }*/

    @RequestMapping(path = {"/vm"}, method = RequestMethod.GET)
    public String template(Model model) {
        // add String
        model.addAttribute("value1", "v11");

        // add list
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        // add map
        Map<String, String> map = new HashMap();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("map", map);

        // add class
        model.addAttribute("User", new User("minmin"));

        return "home";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession session,
                          @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("JSESSIONID" + sessionId + "<br>");

        sb.append("getMethod: " + request.getMethod() + "<br>");
        sb.append("getPathInf: " + request.getPathInfo() + "<br>");
        sb.append("getRequestURI: " + request.getRequestURI() + "<br>");
        sb.append("getQueryString: " + request.getQueryString() + "<br>");

        sb.append("<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name +": " + request.getHeader(name) + "<br>");
        }

        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies())
                sb.append("cookie: " + cookie.getName() + "\tvalue:\t" + cookie.getValue() + "<br>");
        }


        // add response header
        response.addHeader("NowCode", "hello");
        // add response cookie
        response.addCookie(new Cookie("NowCode", "minmin"));

        // add response outputstream
        //response.getOutputStream();

        return sb.toString();
    }

    // 302 redirect
    @RequestMapping(value = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {
        httpSession.setAttribute("msg", "I love minmin");
        return "redirect:/";
    }

    // 301 redirect
    @RequestMapping(value = "/redirect2/{code}")
    public RedirectView redirect2(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "I love minmin");

        RedirectView r = new RedirectView("/",true);
        if(code == 301) {
            r.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return r;
    }

    // admin
    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String redirect(@RequestParam("key") String key) {
        if("admin".equals(key)) {
            return "hello admin";
        } else {
            throw new IllegalArgumentException("error lueleuleu");
        }

    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error: " + e.getMessage();
    }
}
