package com.cmfz.vo;

/**
 * @Author Quan Xiang Zeng
 * @Description: TODO
 * @CreateTime 2019-12-20 21:22
 */
public enum AdminOther {
    ERROR_USERAnd(1000, "用户名或者错误！");


    private Integer code;
    private String message;

    private AdminOther(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
