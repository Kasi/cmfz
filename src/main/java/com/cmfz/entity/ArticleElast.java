package com.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @className: ArticleElast
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-26 14:38
 * @Version 1.0
 */

@Document(indexName = "article", type = "articledata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleElast {
    @Id
    private String id;


    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")

    private String content;

}
