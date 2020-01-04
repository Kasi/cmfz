package com.cmfz.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: LoginInterceptor
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-22 13:26
 * @Version 1.0
 */


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object admin = request.getSession().getAttribute("admin");
        //默认不拦截jsp 放WEB-INF 就行了
        return true;
/*        if (admin == null) {
            request.getRequestDispatcher("/jsp/adminLogin.jsp").forward(request, response);
            // response.sendRedirect(request.getContextPath()+ "/jsp/adminLogin.jsp");
            return false;
        }
        return true;*/
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
