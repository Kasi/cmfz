package com.cmfz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.cmfz.dao"})
//搜索引擎扫描开启
/*@EnableElasticsearchRepositories(basePackages = {"com.cmfz.dao"})*/
public class App {

    public static void main(String[] args) {
        //解决netty冲突导致es连接不上
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(App.class, args);

    }


}
