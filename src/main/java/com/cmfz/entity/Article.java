package com.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Article {
    private String id;
    private String author_Name;
    private Date article_Time;

    private String article_Title;
    private String author_Id;
    private Integer is_Delete;
    private Integer is_Show;

}
