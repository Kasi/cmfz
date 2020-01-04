package com.cmfz.aops;

import com.cmfz.service.impl.ArticleServiceImpl;
import com.cmfz.service.impl.GoEastServiceImpl;
import com.cmfz.vo.Day7VO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @className: EchartsAOP
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-31 9:48
 * @Version 1.0
 */

@Component
@Aspect //声明是一个切面类
public class EchartsAOP {
    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired

    private GoEastServiceImpl goEasyService;


    //声明切入点
    // 不关心返回值 但是是com.cmfz.service.impl.ArticleServiceImpl.addArticle 的方法 参数类型数量不关心
    @Pointcut(value = ("execution(* com.cmfz.service.impl.ArticleServiceImpl.addArticle(..))"))
    public void point() {


    }

    //  后置通知 方法执行完后执行

    //获得最近7天文章发表数并发送数据到前台

    //使用切入点 组装切面
    @After("point()")

    public void getDay7AndgoEasy(JoinPoint joinPoint) {


        //查询出 7 天数据

        List<Day7VO> day7Article = articleService.getDay7Article();


        // 创建jackson 对象映射模型
        ObjectMapper mapper = new ObjectMapper();

        try {

            //用于区分消息
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("article", day7Article);

            String value = mapper.writeValueAsString(hashMap);

            goEasyService.goEasy("BC-c5d32e6f4b5d43f39bda54d7d8ed8086", "http://rest-hangzhou.goeasy.io", value, "goeasyTest");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
