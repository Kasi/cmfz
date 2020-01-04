package com.cmfz.service;

import com.cmfz.entity.Option;
import com.cmfz.entity.SlideShow;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @className: Slide_Show
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-23 20:50
 * @Version 1.0
 */

@Repository
public interface SlideShowService {

    List<SlideShow> queryAllImgages(SlideShow slideShow);

    void changeImage(SlideShow slideShow);

    void removerImage(String[] ids);

    void addImage(SlideShow slideShow);

    Map<String, Object> queryPage(Option option);

    int totalDataNum();
}
