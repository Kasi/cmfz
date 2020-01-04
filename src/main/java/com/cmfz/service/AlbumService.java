package com.cmfz.service;

import com.cmfz.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-24 15:48
 * @Description TODO
 */

public interface AlbumService {
    /**
     * @param album 专辑对象
     * @return 数据结构 集合 类型 Album
     */
    List<Album> queryAlbum(Album album);

    /**
     * @param album 专辑对象
     * @return 影响行数
     */
    void addAlbum(Album album);

    /**
     * @param albumids 专辑对象 id
     * @return 影响行数
     */
    void removerAlbum(@Param("aids") String[] albumids);

    /**
     * @param album 专辑对象
     * @return 影响行数
     */
    void changeAlbum(Album album);

    /**
     * @param aids 多个id
     * @return 数据结构 集合 类型 Album
     */
    List<Album> queryByIds(@Param("aids") String[] aids);

    /**
     * @param start 起始条数
     * @return 数据结构 集合 返回多个 Album
     */
    Map<String, Object> queryPage(@Param("start") Integer start);

    int queryTotalNumber();

    List<Integer> queryYear();


}
