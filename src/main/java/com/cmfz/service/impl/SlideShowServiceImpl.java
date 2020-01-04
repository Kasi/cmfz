package com.cmfz.service.impl;

import com.cmfz.dao.SlideShowDao;
import com.cmfz.entity.Option;
import com.cmfz.entity.SlideShow;
import com.cmfz.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: SlideShowImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-23 20:52
 * @Version 1.0
 */

@Service
@Transactional
public class SlideShowServiceImpl implements SlideShowService {
    @Autowired
    private SlideShowDao slideShowDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SlideShow> queryAllImgages(SlideShow slideShow) {
        return slideShowDao.select(slideShow);
    }

    @Override
    public void changeImage(SlideShow slideShow) {
        System.out.println(slideShow);
        slideShowDao.update(slideShow);
    }

    @Override
    public void removerImage(String[] ids) {
        slideShowDao.delete(ids);
    }

    @Override
    public void addImage(SlideShow slideShow) {
        slideShowDao.insert(slideShow);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryPage(Option option) {
   /*     属性	备注
        total	总页数
        page	当前页
        records	查询出的记录数
        rows	包含实际数据的数组
        id	行id
        cell	当前行的所有单元格*/

        Map<String, Object> map = new HashMap<>();
        //总条数
        int i = totalDataNum();
        //总页数
        int total = i % 5 == 0 ? i / 5 : i / 5 + 1;
        //起始页
        int page = (option.getPage() - 1) * 5;

        map.put("total", total);

        map.put("page", option.getPage());

        map.put("records", i);

        List<SlideShow> shows = slideShowDao.selectPage(page);

        map.put("rows", shows);

        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int totalDataNum() {
        return slideShowDao.selectCont();
    }
}
