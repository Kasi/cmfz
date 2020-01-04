package com.cmfz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: DispatcherController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-22 16:59
 * @Version 1.0
 */

@Controller
public class DispatcherController {
    @RequestMapping("/bgmange")
    public String bgmange() {
        //使用forward转发
        return "bgmange";

    }
}
