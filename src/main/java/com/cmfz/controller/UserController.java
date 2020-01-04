package com.cmfz.controller;

import com.cmfz.entity.User;
import com.cmfz.service.UserService;
import com.cmfz.service.impl.UserServiceImpl;
import com.cmfz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: UserController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2020-01-01 22:07
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResultBean<Map<String, Object>> regitser(User user) {

        userService.insert(user);

        //正常的话会执行下面

        ResultBean<Map<String, Object>> bean = new ResultBean<Map<String, Object>>();

        bean.setMsg("注册成功！");

        //设置状态
        bean.setCode(1);
        //  得到状态容器
        HashMap<String, Object> map = new HashMap<>();

        //返回注册前信息
        map.put("result", user);

        bean.setData(map);
        return bean;
    }

    /**
     * 重置密码
     *
     * @return 返回状态码和描述信息
     */
    @PutMapping("/resetPwd")
    public ResultBean<Map<String, Object>> changeUserPassWord(User user) {


        /*
         * 1. 校验预留信息是否正确
         * 2. 正确进行重置 =>提示
         * 3. 不正确提示
         * */

        // 得到传来的手机号
        String tel = user.getTel();

        ResultBean<Map<String, Object>> bean = ((UserServiceImpl) userService).resetPassWord(user);

        return bean;


    }

    /**
     * @param user 用户信息
     * @return 返回结果
     */
    @PostMapping("/login")
    public ResultBean<Map<String, Object>> login(User user, ModelMap modelMap) {

        ResultBean<Map<String, Object>> resultBean = ((UserServiceImpl) userService).login(user, modelMap);

        return resultBean;

    }


}
