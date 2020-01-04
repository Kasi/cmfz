package com.cmfz.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @className: LoginConfig
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-22 13:32
 * @Version 1.0
 */

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        InterceptorRegistration interceptor = registry.addInterceptor(new LoginInterceptor());
        //拦截所有
        interceptor.addPathPatterns("/**");

        //排除
        interceptor.excludePathPatterns("/jsp/adminLogin.jsp", "/validate/validateImg", "/admin/login"
                , "/**/*.js"
                , "/**/*.css"
                , "/**/*.jpg"
                , "/**/*.woff2"
                , "/**/*.woff"
                , "/**/*.ttf"
        );
    }
}
