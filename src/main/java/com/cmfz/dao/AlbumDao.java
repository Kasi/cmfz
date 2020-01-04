package com.cmfz.dao;

import com.cmfz.entity.Album;
import com.cmfz.vo.MonthVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-24 15:11
 * @Description TODO
 */
@Repository
public interface AlbumDao {
    /**
     * @param album 专辑对象
     * @return 数据结构 集合 类型 Album
     */
    List<Album> select(Album album);

    /**
     * @param album 专辑对象
     * @return 影响行数
     */
    int insert(Album album);

    /**
     * @param albumids 专辑对象 id
     * @return 影响行数
     */
    int delete(@Param("aids") String[] albumids);

    /**
     * @param album 专辑对象
     * @return 影响行数
     */
    int update(Album album);

    /**
     * @param aids 多个id
     * @return 数据结构 集合 类型 Album
     */
    List<Album> selectByIds(@Param("aids") String[] aids);

    /**
     * @param start 起始条数
     * @return 数据结构 集合 返回多个 Album
     */
    List<Album> selectPage(@Param("start") Integer start);


    int selectTotalNumber();

    /**
     * @return 返回每月添加的专辑数量
     */

    List<MonthVO> selectYear();

}
