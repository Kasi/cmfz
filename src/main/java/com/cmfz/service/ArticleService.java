package com.cmfz.service;

import com.cmfz.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: ArticleService
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-25 20:11
 * @Version 1.0
 */


public interface ArticleService {

    List<Article> queryArticle(Article article);

    void changeArticle(Article article);

    /**
     * 用途 根据文章ids批量删除
     *
     * @param ids       文章ids
     * @param author_id 作者id
     * @return
     */
    void removerArticle(@Param("ids") String[] ids, @Param("author_id") String author_id);

    /**
     * 根据文章ids删除
     *
     * @param ids 文章ids
     * @return
     */
    void removerArticleByids(@Param("ids") String[] ids);

    /**
     * 删除该作者所有的文章
     *
     * @param author_id 作者id
     * @return
     */
    void removerByAuthorId(@Param("author_id") String author_id);

    /**
     * 分页查询
     *
     * @param start 起始条数
     * @return
     */
    Map<String, Object> queryPage(@Param("start") Integer start);

    /**
     * @return 查询文章总条数
     */
    int queryTotalNumber();

    /**
     * 查询某个作者发了多少文章
     *
     * @param author_id 作者id
     * @return
     */
    int queryTotalByAuthor(@Param("author_id") String author_id);


    void addArticle(Article article, String content);
}
