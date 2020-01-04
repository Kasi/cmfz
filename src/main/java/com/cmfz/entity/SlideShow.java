package com.cmfz.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SlideShow implements Serializable {
    @Excel(name = "id", width = 15)
    private String id;

    //类型 图片
    @Excel(name = "img_Path", type = 2, width = 100, height = 50, imageType = 1)
    private String img_Path;

    @Excel(name = "img_Name")
    private String img_Name;

    @Excel(name = "is_Delete")
    private String is_Delete;
}
