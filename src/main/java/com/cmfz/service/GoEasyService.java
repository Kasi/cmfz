package com.cmfz.service;

import org.springframework.stereotype.Repository;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-31 8:39
 * @Description TODO
 */
@Repository
public interface GoEasyService<T> {


    void goEasy(String appkey, String host, T content, String channel);

}
