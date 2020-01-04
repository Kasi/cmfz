package com.cmfz.dao;

import com.cmfz.entity.SlideShow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-22 18:07
 * @Description TODO
 */
@Repository
public interface SlideShowDao {

    List<SlideShow> select(SlideShow slideShow);

    void update(SlideShow slideShow);

    int delete(@Param("ids") String[] id);

    int insert(SlideShow slideShow);

    int inserts(@Param("list") List<SlideShow> slideShows);

    List<SlideShow> selectPage(@Param("page") Integer page);


    int selectCont();

}
