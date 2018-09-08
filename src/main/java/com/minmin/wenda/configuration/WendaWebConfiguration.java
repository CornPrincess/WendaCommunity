package com.minmin.wenda.configuration;

import com.minmin.wenda.interceptor.LoginRequiredInterceptor;
import com.minmin.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author corn
 * @version 1.0.0
 */

@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 在系统初始化的时候添加创建的拦截器
        registry.addInterceptor(passportInterceptor);

        // 之后再添加登录跳转拦截器
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");

        super.addInterceptors(registry);
    }
}
