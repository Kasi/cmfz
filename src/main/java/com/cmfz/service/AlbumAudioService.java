package com.cmfz.service;

import com.cmfz.entity.AlbumAudio;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-24 15:56
 * @Description TODO
 */
public interface AlbumAudioService {

    /**
     * @param albumAudio 音频对象
     * @return 数据结构 集合 返回多个 AlbumAudioDao
     */
    List<AlbumAudio> queryAlbumAudio(AlbumAudio albumAudio);

    /**
     * @param albumAudio 音频对象
     * @return 影响行数
     */
    int addAlbumAudio(AlbumAudio albumAudio);

    /**
     * @param albumAudio 音频对象
     * @return 影响行数
     */
    int changeAlbumAudio(AlbumAudio albumAudio);

    /**
     * @param aaids 音频数组
     * @return 影响行数
     */
    int removerAlbumAudio(@Param("aaids") String[] aaids);

    /**
     * @param start 起始条数
     * @return 数据结构 集合 返回多个 AlbumAudioDao
     */
    Map<String, Object> queryPage(@Param("start") Integer start, String parentId);

    List<AlbumAudio> queryByIds(String[] ids);

    int queryTotalNumber(String parentId);

    void removerByParentIds(String[] pids);
}
