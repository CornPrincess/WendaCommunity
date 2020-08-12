package com.minmin.wenda.controller;

import com.minmin.wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author corn
 * @version 1.0.0
 */

@Controller
@RequestMapping("/demo")
public class Demo {
    private static final Logger log = LoggerFactory.getLogger(Demo.class);

    /*
        value， method；
        value：   指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
        method：  指定请求的method类型， PUT、GET、DELETE、POST 分别对应注解@PutMapping @GetMapping @DeleteMapping @PostMapping；
                  没有默认值，如果不配置method，则以任何请求形式

        consumes，produces；
        consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
        produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；

        params，headers；
        params： 指定request中必须包含某些参数值是，才让该方法处理。
        headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求。
     */
    @RequestMapping(value = "/method0")
    @ResponseBody
    public String method0() {
        return "method0";
    }


    @RequestMapping(value = {"/method1", "/method1/second"})
    @ResponseBody
    public String method1() {
        return "method1";
    }

    @RequestMapping(value = "/method2", method = RequestMethod.POST)
    @ResponseBody
    public String method2() {
        return "method2";
    }

    @RequestMapping(value = "/method3", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String method3() {
        return "method3";
    }


    @RequestMapping(value = "/method4", headers = "name=pankaj")
    @ResponseBody
    public String method4() {
        return "method4";
    }

    @RequestMapping(value = "/method5", headers = {"name=pankaj", "id=1"})
    @ResponseBody
    public String method5() {
        return "method5";
    }

    @RequestMapping(value = "/method6", produces = {"application/json", "application/xml"}, consumes = "text/html")
    @ResponseBody
    public String method6() {
        return "method6";
    }

    @RequestMapping(value = "/method7/{id}")
    @ResponseBody
    public String method7(@PathVariable("id") int id) {
        return "method7 with id =" + id;
    }

    @RequestMapping(value = "/method8/{id:[\\d]+}/{name}")
    @ResponseBody
    public String method8(@PathVariable("id") long id, @PathVariable("name") String name) {
        return "method8 with id = " + id + " and name =" + name;
    }

    @RequestMapping(value = "/method9")
    @ResponseBody
    public String method9(@RequestParam("id") int id) {
        return "method9 with id = " + id;
    }

    @RequestMapping(path = {"/velocity"}, method = RequestMethod.GET)
    public String template(Model model) {
        // add String
        model.addAttribute("value1", "v11");

        // add list
        List<String> colors = Arrays.asList("RED", "GREEN", "BLUE");
        model.addAttribute("colors", colors);

        // add map
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("map", map);

        // add class
        // no use
        model.addAttribute("user", new User("minmin"));

        // effect
        // add only get method, can use user.description user.getDescription()
        User user = new User("xiaowei");
        model.addAttribute("User", user);

        return "demo";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session,
                          @CookieValue("JSESSIONID") String sessionId) throws IOException {
        String sb = request.getMethod() + "<br>" +
                request.getQueryString() + "<br>" +
                request.getPathInfo() + "<br>" +
                request.getRequestURI() + "<br>";
        sb += "sessionId: " + sessionId;

        response.addHeader("minmin", "hello");
        response.addCookie(new Cookie("wei1", "love"));
        // 可用来返回验证码
//        response.getOutputStream().write(1);
        return sb;
    }

    // 302 redirect 临时跳转
    @RequestMapping(value = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {
        httpSession.setAttribute("msg", "I love minmin" + code);
        return "redirect:/demo/test";
    }

    // 301 redirect 强制跳转，永久跳转，浏览器会从缓存中直接读取
    // 跳转的作用：1. 如果检测到客户端是手机，直接跳转到对应到手机页面
    //            2. 如果之前发布到页面不能使用，可以直接跳转到老的页面
    @RequestMapping(value = "/redirect2/{code}")
    public RedirectView redirect2(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "I love minmin " + code);
        RedirectView redirectView = new RedirectView("/demo/test", true);
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String redirectTest(HttpSession httpSession) {
        return "" + httpSession.getAttribute("msg");
    }

    @RequestMapping()
    @ResponseBody
    public String defaultMethod() {
        return "default method";
    }

    @RequestMapping("*")
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        log.info("welcome to admin");
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("error key");
    }

    @ResponseBody
    public String fallbackMethod() {
        return "fallback method";
    }

    @ExceptionHandler
    @ResponseBody
    public String errorHandler(Exception e) {
        return "Error: " + e.getMessage();
    }

}
