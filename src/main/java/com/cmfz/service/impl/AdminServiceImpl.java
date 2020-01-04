package com.cmfz.service.impl;

import com.cmfz.dao.AdminDao;
import com.cmfz.entity.Admin;
import com.cmfz.service.AdminService;
import com.cmfz.vo.Admin_vo;
import com.cmfz.vo.MyOther;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @className: AdminServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 21:17
 * @Version 1.0
 */

@Service

public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin_vo queryByName(String userName, String passWord, Model model) {
        Admin admin = adminDao.selectByName(userName);
        //提示信息
        Admin_vo admin_vo = new Admin_vo();
        if (admin != null) {
            //取得盐
            String salt = MyOther.MD5_salt.getMessage();
            //密码加盐值
            passWord += salt;
            //返回MD5对应密码值
            String md5v = DigestUtils.md5Hex(passWord);
            if (!md5v.equals(admin.getPass_word())) {
                //不匹配
                admin_vo.setMess(-1, Date.valueOf(LocalDate.now()));
                return admin_vo;
            } else {
                //admin对象存session
                model.addAttribute("admin", admin);
                admin_vo.setMess(1, Date.valueOf(LocalDate.now()));
                //修改登录时间
                adminDao.update(java.sql.Date.valueOf(LocalDate.now()), admin.getId());
                //设置返回json
                return admin_vo;
            }
        } else {
            admin_vo.setMess(-1, Date.valueOf(LocalDate.now()));
            return admin_vo;
        }

    }


}
