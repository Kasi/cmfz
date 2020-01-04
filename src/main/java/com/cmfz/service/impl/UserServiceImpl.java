package com.cmfz.service.impl;

import com.cmfz.dao.UserDao;
import com.cmfz.entity.User;
import com.cmfz.service.UserService;
import com.cmfz.vo.ResultBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @className: UserServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-23 9:17
 * @Version 1.0
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired

    private UserDao userDao;


    @Override
    public int deleteByPrimaryKey(String id) {
        return userDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        //得到uuid
        String uuid = UUID.randomUUID().toString();

        // M D 5 加密密码
        String cps = DigestUtils.md5Hex(record.getPwd() + uuid);

        record.setSalt(uuid);
        //设置加密后的密码
        record.setPwd(cps);
        return userDao.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userDao.insertSelective(record);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }

    /**
     * 激活用户
     *
     * @param uid 用户Id
     */
    public void activeUser(String uid) {
        User user = new User();
        user.setId(uid);
        // 2 冻结 1 激活
        user.setStatus(1);
        userDao.updateByPrimaryKeySelective(user);
    }

    /**
     * 冻结用户
     *
     * @param uid 用户id
     */
    public void freezeUser(String uid) {
        User user = new User();
        user.setId(uid);
        // 2 冻结 1 激活
        user.setStatus(2);
        userDao.updateByPrimaryKeySelective(user);
    }

    /**
     * 重置密码
     *
     * @return 返回信息
     */
    public ResultBean<Map<String, Object>> resetPassWord(User userx) {
        ResultBean<Map<String, Object>> bean = new ResultBean<>(new HashMap<String, Object>());

        Map<String, Object> data = bean.getData();
        String tel = userx.getTel();

        String uid = userx.getId();


        // 任意是否不满足
        if (ObjectUtils.isEmpty(tel) || ObjectUtils.isEmpty(uid)) {
            bean.setCode(-1);
            bean.setMsg("缺少参数！");
            return bean;
        }


        User user = selectByPrimaryKey(uid);

        if (ObjectUtils.isEmpty(user)) {
            //没有这个用户
            bean.setCode(-2);
            bean.setMsg("没有这个用户！");
            return bean;
        }


        String tel1 = user.getTel();

        //手机号一致
        if (tel1.equals(tel)) {
            User user1 = new User();
            user1.setId(uid);

            // 重置密码为123456
            updateByPrimaryKeySelective(user1);


            bean.setCode(1);
            bean.setMsg("重置密码完成！");
        } else {
            bean.setCode(-1);

            bean.setMsg("手机号不正确！");
        }


        return bean;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> selectBySelective(User user) {

        return userDao.selectBySelective(user);
    }

    public ResultBean<Map<String, Object>> login(User user, ModelMap modelMap) {


        // 校验
        if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(user.getPwd()) || ObjectUtils.isEmpty(user.getTel())) {
            ResultBean<Map<String, Object>> bean = new ResultBean<>("无效参数", -1);
            return bean;
        }
        //查找用户
        List<User> users = selectBySelective(user);

        if (users.size() == 1) {
            // 登录成功 保存当前用户对象

            modelMap.addAttribute("user", users.get(0));

            //组装返回信息
            ResultBean<Map<String, Object>> bean = new ResultBean<>("登录成功", 1);

            bean.setData(new HashMap<String, Object>());

            bean.getData().put("user", users.get(0));
            return bean;
        } else {
            //登录失败
            ResultBean<Map<String, Object>> bean = new ResultBean<>("账号或者密码错误", -1);
            return bean;
        }


    }
}

