package com.minmin.wenda.interceptor;

import com.minmin.wenda.dao.LoginTicketDAO;
import com.minmin.wenda.dao.UserDAO;
import com.minmin.wenda.model.HostHolder;
import com.minmin.wenda.model.LoginTicket;
import com.minmin.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author corn
 * @version 1.0.0
 */

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 定义cookie的值
        String ticket = null;
        if(request.getCookies() != null) {
            // 查找是否存在ticket的cookie
            for(Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if(ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            // 验证查找到的ticket是否符合要求：不为null， 没过期， 状态为0
            if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }

            // 通过ticket找到对应的user对象
            User user = userDAO.selectById(loginTicket.getUserId());
            // 在拦截器最早的时候，将用户信息放入Threadlocal，后面所有的请求都可以访问这个变量
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO modelAndView, 在渲染之前将user放入到model中，这样每个model都能用到。
        if (modelAndView != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空Threadlocal里的用户
        hostHolder.clear();
    }
}
