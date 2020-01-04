package com.cmfz.service.impl;

import com.cmfz.dao.AlbumDao;
import com.cmfz.entity.Album;
import com.cmfz.service.AlbumAudioService;
import com.cmfz.service.AlbumService;
import com.cmfz.vo.MonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @className: AlbumServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-24 15:50
 * @Version 1.0
 */

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumAudioService albumAudioService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Album> queryAlbum(Album album) {
        return albumDao.select(album);
    }

    @Override
    public void addAlbum(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void removerAlbum(String[] albumids) {
        albumDao.delete(albumids);
        //批量删除某专辑下所有音频
        albumAudioService.removerByParentIds(albumids);
    }

    @Override
    public void changeAlbum(Album album) {
        albumDao.update(album);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Album> queryByIds(String[] aids) {
        return albumDao.selectByIds(aids);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryPage(Integer start) {

/*        属性	备注
        total	总页数
        page	当前页
        records	查询出的记录数
        rows	包含实际数据的数组
        id	行id
        cell	当前行的所有单元格*/
        int recoreds = queryTotalNumber();

        int total = recoreds % 5 == 0 ? recoreds / 5 : recoreds / 5 + 1;

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);

        map.put("page", start);

        map.put("records", recoreds);

        start = (start - 1) * 5;

        map.put("rows", albumDao.selectPage(start));

        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int queryTotalNumber() {
        return albumDao.selectTotalNumber();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Integer> queryYear() {
        //保存月份
        ArrayList<Integer> months = new ArrayList<>();

        //保存每月专辑数量
        ArrayList<Integer> OneMonths = new ArrayList<>();

        for (MonthVO vo : albumDao.selectYear()) {
            months.add(vo.getMonth());
            OneMonths.add(vo.getNumber());
        }

        //创建空白数组

        Integer[] monthss = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //将没有月份的筛选出来
        replaceValue(monthss, months, OneMonths);


        return Arrays.asList(monthss);

    }

    /**
     * @param arr  空白数组
     * @param arr1 月份数组
     * @param arr2 月份值数组
     */
    public void replaceValue(Integer[] arr, List<Integer> arr1, List<Integer> arr2) {
        //遍历空白

        try {
            for (int i = 0; i < arr1.size(); i++) {
                for (int j = 0; j < arr.length; j++) {
                    //这个月份存在
                    if (arr1.get(i) == j + 1) {

                        arr[arr1.get(i) - 1] = arr2.get(i);

                        break;
                    }

                }

            }
        } catch (ArrayIndexOutOfBoundsException array) {
            array.printStackTrace();
            System.out.println("数组越界啦：代码行：148");

        }


    }
}
