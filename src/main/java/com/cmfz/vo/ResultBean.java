package com.cmfz.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: ResultBean
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2020-01-02 16:32
 * @Version 1.0
 */

@Data
public class ResultBean<T> implements Serializable {
    public static final int FAIL = 1;

    public static final int SUCCESS = 0;

    private String msg = null;
    private Integer code = null;

    private T data;

    public ResultBean() {
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(Throwable throwable) {
        this.msg = throwable.toString();
        this.code = FAIL;
    }

    public ResultBean(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }


}
