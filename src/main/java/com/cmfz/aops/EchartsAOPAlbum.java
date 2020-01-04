package com.cmfz.aops;

import com.cmfz.service.impl.AlbumServiceImpl;
import com.cmfz.service.impl.GoEastServiceImpl;
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
 * @className: EchartsAOPAlbum
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-31 14:42
 * @Version 1.0
 */

@Component
@Aspect //声明切面类
public class EchartsAOPAlbum {
    @Autowired
    private AlbumServiceImpl albumService;

    @Autowired
    private GoEastServiceImpl goEastService;

    // 声明切入点
    @Pointcut(value = "execution(* com.cmfz.service.impl.AlbumServiceImpl.addAlbum(..))")
    public void pointcut() {

    }

    //组装切面
    @After(value = "pointcut()")
    public void after(JoinPoint joinPoint) {
        // 每月专辑发布数量
        List<Integer> integers = albumService.queryYear();

        //通过goeasy 通知前台

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("album", integers);

        // 创建jackson 模型
        ObjectMapper mapper = new ObjectMapper();

        try {
            String value = mapper.writeValueAsString(hashMap);

            goEastService.goEasy("BC-c5d32e6f4b5d43f39bda54d7d8ed8086", "http://rest-hangzhou.goeasy.io", value, "goeasyTest");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
