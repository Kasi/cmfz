package com.cmfz.service;

import com.cmfz.vo.Admin_vo;
import org.springframework.ui.Model;

/**
 * @className: AdminService
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 21:07
 * @Version 1.0
 */

public interface AdminService {

    /**
     * @param userName 管理员名
     * @param passWord 管理员密码
     * @return
     */
    Admin_vo queryByName(String userName, String passWord, Model model);


}
