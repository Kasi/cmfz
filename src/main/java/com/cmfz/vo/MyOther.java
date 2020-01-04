package com.cmfz.vo;

/**
 * @Author Quan Xiang Zeng
 * @Description: TODO
 * @CreateTime 2019-12-20 21:13
 */
public enum MyOther {
    MD5_salt(1000, "kxkkdlzlfodog2");
    private Integer code;
    private String message;

    private MyOther(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
