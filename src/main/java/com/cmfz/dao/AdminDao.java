package com.cmfz.dao;

import com.cmfz.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * @className: AdminDao
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 20:08
 * @Version 1.0
 */

@Repository
public interface AdminDao {

    /**
     * @param userName 管理员名
     * @return 类型 Admin
     */
    Admin selectByName(@Param("userName") String userName);

    int update(@Param("time") Date date, @Param("id") String id);


}
