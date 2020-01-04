package com.cmfz.controller;

import com.cmfz.entity.SlideShow;
import com.cmfz.service.SlideShowService;
import com.cmfz.service.impl.ExcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @className: ExcelExportController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-27 17:26
 * @Version 1.0
 */


@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    private ExcelServiceImpl excelService;

    @Autowired
    private SlideShowService slideShowService;

    /*
     * 数据的流入 数据的流出
     * */

    /*
     *Excel 导出
     * */
    @GetMapping("/export")
    public Map<String, Object> excelExport(HttpServletResponse response, HttpServletRequest request) {

        //查出所有轮播图数据
        List<SlideShow> slideShows = slideShowService.queryAllImgages(new SlideShow());

        //数据交给excel处理
        Map<String, Object> map = excelService.excelExportFile(slideShows, request, response);

        return map;
    }

    /*
     * Excel上传
     * */
    @RequestMapping("/upload")
    public Map<String, Object> excelUpload(MultipartFile excelFile, HttpServletRequest request) {

        //excel数据上传到数据库
        excelService.excelUPloadFile(excelFile, request);


        return null;
    }


}
