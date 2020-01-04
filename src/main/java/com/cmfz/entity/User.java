package com.cmfz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;

    private String headUrl;

    private String name;

    private String tel;

    private String pwd;

    private String tName;

    private String sex;

    private String address;

    private String signature;

    private Date regTime;

    private Date loginTime;

    private Integer status;

    @JsonIgnore
    private String salt;

    private static final long serialVersionUID = 1L;


}