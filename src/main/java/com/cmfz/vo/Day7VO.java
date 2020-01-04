package com.cmfz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Quan Xiang Zeng
 * @Description: TODO
 * @CreateTime 2019-12-20 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Day7VO {
    private String week;
    private Integer number;
}
