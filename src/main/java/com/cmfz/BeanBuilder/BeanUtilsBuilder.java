package com.cmfz.BeanBuilder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;

/**
 * @className: BeanUtilsBuilder
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-25 20:24
 * @Version 1.0
 */
//允许在上下文中注册额外的Bean或导入其他配置类，相当于Bean文件
@Configuration
public class BeanUtilsBuilder {

    //多例
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public HashMap<String, Object> newMapMsgBean() {
        System.out.println(2222);
        return new HashMap<String, Object>();
    }
}
