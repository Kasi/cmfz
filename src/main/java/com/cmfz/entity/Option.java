package com.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @className: Option
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-24 8:35
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Option {
    private Boolean _search;
    private String nd;
    private Integer rows;
    private Integer page;
    private Integer sidx;
    private String sord;
    private String[] filters;
    private String oper;
    private String[] id;
}
