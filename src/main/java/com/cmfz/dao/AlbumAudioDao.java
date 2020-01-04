package com.cmfz.dao;

import com.cmfz.entity.AlbumAudio;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-24 15:15
 * @Description TODO
 */
public interface AlbumAudioDao {
    /**
     * @param albumAudio 音频对象
     * @return 数据结构 集合 返回多个 AlbumAudioDao
     */
    List<AlbumAudio> select(AlbumAudio albumAudio);

    /**
     * @param albumAudio 音频对象
     * @return 影响行数
     */
    int insert(AlbumAudio albumAudio);

    /**
     * @param albumAudio 音频对象
     * @return 影响行数
     */
    int update(AlbumAudio albumAudio);

    /**
     * @param aaids 音频数组
     * @return 影响行数
     */
    int delete(@Param("aaids") String[] aaids);

    /**
     * @param start 起始条数
     * @return 数据结构 集合 返回多个 AlbumAudioDao
     */
    /**
     * 批量删除 专辑下所有 音频
     *
     * @param pids 专辑id
     * @return 影响行数
     */
    int deleteByParentId(@Param("pids") String[] pids);

    List<AlbumAudio> selectPage(@Param("start") Integer start, @Param("parentId") String parentId);

    List<AlbumAudio> selectByIds(@Param("idss") String[] ids);

    int selectTotalNumber(@Param("parentId") String parentId);
}
