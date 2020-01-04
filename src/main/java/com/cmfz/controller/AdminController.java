package com.cmfz.controller;

import com.cmfz.entity.Admin;
import com.cmfz.service.AdminService;
import com.cmfz.vo.Admin_vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: AdminController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 21:29
 * @Version 1.0
 */

@RestController
@RequestMapping("/admin")
@SessionAttributes(names = {"admin"}, types = {Admin.class})
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "/login{userName}{passWord}")
    public Admin_vo login(String userName, String passWord, String code, Model model, @CookieValue("JSESSIONID") String unique) {
        boolean b = validateCodeCheck(code, unique);
        if (!b) {
            //验证码不通过
            return new Admin_vo(-2, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        //返回状态VO
        Admin_vo admin_vo = adminService.queryByName(userName, passWord, model);
        //返回前台
        return admin_vo;
    }

    /**
     * 验证码是否正确
     *
     * @param code   验证码
     * @param unique jesssionID
     * @return 正确 true
     */
    public boolean validateCodeCheck(String code, String unique) {
        //存在key删除 返回1 不存在 0
        Long aBoolean = stringRedisTemplate.opsForHash().delete(unique, code);
        return aBoolean == 1 ? true : false;
    }
}
