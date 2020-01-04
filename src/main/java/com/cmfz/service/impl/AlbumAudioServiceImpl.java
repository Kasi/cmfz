package com.cmfz.service.impl;

import com.cmfz.dao.AlbumAudioDao;
import com.cmfz.entity.AlbumAudio;
import com.cmfz.service.AlbumAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: AlbumAudioServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-24 16:04
 * @Version 1.0
 */

@Service
@Transactional
public class AlbumAudioServiceImpl implements AlbumAudioService {
    @Autowired

    private AlbumAudioDao albumAudioDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AlbumAudio> queryAlbumAudio(AlbumAudio albumAudio) {
        return albumAudioDao.select(albumAudio);
    }

    @Override
    public int addAlbumAudio(AlbumAudio albumAudio) {
        return albumAudioDao.insert(albumAudio);
    }

    @Override
    public int changeAlbumAudio(AlbumAudio albumAudio) {
        return albumAudioDao.update(albumAudio);
    }

    @Override
    public int removerAlbumAudio(String[] aaids) {
        return albumAudioDao.delete(aaids);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryPage(Integer start, String parentId) {

/*        属性	备注
        total	总页数
        page	当前页
        records	查询出的记录数
        rows	包含实际数据的数组
        id	行id
        cell	当前行的所有单元格*/
        int records = queryTotalNumber(parentId);

        int total = records % 5 == 0 ? records / 5 : records / 5 + 1;

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);

        map.put("page", start);

        map.put("records", records);

        start = (start - 1) * 5;

        map.put("rows", albumAudioDao.selectPage(start, parentId));


        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int queryTotalNumber(String parentId) {
        return albumAudioDao.selectTotalNumber(parentId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AlbumAudio> queryByIds(String[] ids) {
        return albumAudioDao.selectByIds(ids);
    }

    @Override
    public void removerByParentIds(String[] pids) {
        albumAudioDao.deleteByParentId(pids);
    }
}
