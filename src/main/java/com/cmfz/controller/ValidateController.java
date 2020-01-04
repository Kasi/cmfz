package com.cmfz.controller;

import com.cmfz.utils.ValidateImageCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @className: ValidateController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 23:22
 * @Version 1.0
 */

@RestController
@RequestMapping("/validate")
public class ValidateController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/validateImg")
    public void getValidateImg(@CookieValue("JSESSIONID") String unique, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        //生成的验证码放redis
        String code = ValidateImageCodeUtils.getSecurityCode();
        cacheValidate(unique, code);
        BufferedImage image = ValidateImageCodeUtils.createImage(code);
        ImageIO.write(image, "jpg", outputStream);
    }

    public void cacheValidate(String unique, String code) {
        //获得redis操作对象
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        //unique大键
        //存入验证码
        hash.put(unique, code, "");
        stringRedisTemplate.expire(unique, 10000, TimeUnit.SECONDS);

    }
}
