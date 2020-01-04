package com.cmfz.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @className: SpringContextUtils
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-21 15:51
 * @Version 1.0
 */


public class SpringContextUtils implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public Object getBean(String name) {
        return context.getBean(name);
    }

    public Object getBean(Class clazz) {
        return context.getBean(clazz);
    }

    public Object getBean(String name, Class clazz) {
        return context.getBean(name, clazz);
    }

}
