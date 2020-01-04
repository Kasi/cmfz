package com.cmfz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: Admin_vo
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-20 21:34
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Admin_vo implements Serializable {
    private int status;
    private String loginTime;

    public void setMess(int status, Date loginTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.status = status;
        this.loginTime = dateFormat.format(loginTime);
    }


}
