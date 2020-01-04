package com.cmfz.dao;

import com.cmfz.entity.Article;
import com.cmfz.vo.Day7VO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-25 19:09
 * @Description TODO
 */
@Repository
public interface ArticleDao {

    List<Article> select(Article article);

    int update(Article article);

    /**
     * 用途 根据文章ids批量删除
     *
     * @param idss      文章ids
     * @param author_id 作者id
     * @return
     */
    int delete(@Param("idss") String[] idss, @Param("author_id") String author_id);

    /**
     * 根据文章ids删除
     *
     * @param ids 文章ids
     * @return
     */
    int deleteByids(@Param("ids") String[] ids);

    /**
     * 删除该作者所有的文章
     *
     * @param author_id 作者id
     * @return
     */
    int deleteByAuthorId(@Param("author_id") String author_id);

    /**
     * 分页查询
     *
     * @param start 起始条数
     * @return
     */
    List<Article> selectPage(@Param("start") Integer start);

    /**
     * @return 查询文章总条数
     */
    int selectTotalNumber();

    /**
     * 查询某个作者发了多少文章
     *
     * @param author_id 作者id
     * @return
     */
    int selectTotalByAuthor(@Param("author_id") String author_id);


    int insert(Article article);


    List<Day7VO> selectDay7();

}
