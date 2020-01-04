package com.cmfz.controller;

import com.cmfz.service.impl.ArticleServiceImpl;
import com.cmfz.vo.Day7VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: EchartsController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-31 9:46
 * @Version 1.0
 */

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private ArticleServiceImpl articleService;


    /**
     * 获取最近七天文章发布数
     *
     * @return
     */
    @GetMapping("/getDay7")
    public List<Day7VO> getDay7() {
        //获取星期1-7的数据
        List<Day7VO> day7Article = articleService.getDay7Article();
        return day7Article;
    }
}
