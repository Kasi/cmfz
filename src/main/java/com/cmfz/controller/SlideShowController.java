package com.cmfz.controller;

import com.cmfz.entity.Option;
import com.cmfz.entity.SlideShow;
import com.cmfz.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * @className: SlideShowController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-23 16:53
 * @Version 1.0
 */

@RestController
@RequestMapping("/slideshow")
public class SlideShowController {
    @Autowired
    private SlideShowService slideShowService;


    @RequestMapping("/image/upload")
    public Map<String, Object> fileUpload(MultipartFile img_Path, HttpServletRequest request, String img_Name, String is_Delete) {
        //拷贝文件
        String filename = copyFile(img_Path, request);
        //轮播图实体
        SlideShow slideShow = new SlideShow();
        //设置图片名
        slideShow.setImg_Name(img_Name);
        //设置图片路径
        slideShow.setImg_Path("images/" + filename);
        //设置ID
        slideShow.setId(UUID.randomUUID().toString());
        //图片状态
        slideShow.setIs_Delete(is_Delete);
        //插入到数据库
        slideShowService.addImage(slideShow);

        Map<String, Object> map = new HashMap<>();
        map.put("msg", slideShow.getImg_Name() + "文件上传成功!");
        return map;
    }

    public String copyFile(MultipartFile img_Path, HttpServletRequest request) {

        String[] strings = new String[1];
        //复制文件到路径
        try {

            img_Path.transferTo(new File(getFilePath(img_Path, request, strings)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings[0];

    }

    //删除文件
    public void deleteFile(String fileNameAndPath) {
        try {
            Files.delete(new File(fileNameAndPath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*img_Name: 3页
    is_Delete: 0
    img_Path: C:\fakepath\3页.png
    oper: add
    id: _empty*/
    @GetMapping("/img/queryPage")
    public Map<String, Object> queryPage(Option option) {
        return slideShowService.queryPage(option);
    }

    //获取文件夹路径
    public String getFilePath(MultipartFile multipartFile, HttpServletRequest request, String... strings) {

        //获取文件名+MIME
        String filename = multipartFile.getOriginalFilename();
        //获取服务器路径
        String path = request.getSession().getServletContext().getRealPath("resources");
        strings[0] = new Date().getTime() + "_" + filename;
        return path + "\\images\\" + strings[0];

    }

    @RequestMapping("/img/update")
    public Map<String, Object> Optionedit(Option option, MultipartFile img_Path, HttpServletRequest request, String is_Delete, String id, String img_Name) {


        if (option.getOper().equals("del")) {


            for (String s : option.getId()) {

                SlideShow slideShow = new SlideShow();

                slideShow.setId(s);

                List<SlideShow> shows = slideShowService.queryAllImgages(slideShow);

                SlideShow show = shows.get(0);


                //返回文件名
                String[] strings = new String[1];
                String path = request.getServletContext().getRealPath("resources") + "\\" + show.getImg_Path();
                boolean exists = new File(path).exists();
                if (exists) {
                    //文件存在就删除
                    //删除文件
                    deleteFile(path);
                }


            }
            //删除 数据库数据

            slideShowService.removerImage(option.getId());


        }

        if (option.getOper().equals("edit")) {

            //修改了图片

            if (img_Path != null && img_Path.getSize() > 0) {
                SlideShow slideShow = new SlideShow();
                slideShow.setId(id);
                List<SlideShow> shows = slideShowService.queryAllImgages(slideShow);

                SlideShow show = shows.get(0);
                //获得文件路径
                String imgPath = show.getImg_Path();
                //删除原文件
                deleteFile(request.getServletContext().getRealPath("resources/") + imgPath);
                //写入数据库
                if (!img_Name.equals("")) {
                    show.setImg_Name(img_Name);
                }
                if (!is_Delete.equals("")) {
                    show.setIs_Delete(is_Delete);
                }


                //写入新文件
                String img_name = copyFile(img_Path, request);
                //设置图片路径
                show.setImg_Path("images/" + img_name);
                //修改数据库
                slideShowService.changeImage(show);


            } else {
                SlideShow slideShow = new SlideShow();
                slideShow.setId(id);
                //校验空字符串
                if (!img_Name.equals("")) {
                    slideShow.setImg_Name(img_Name);
                } else {
                    slideShow.setImg_Name(null);
                }

                slideShow.setIs_Delete(is_Delete);
                //修改数据库
                slideShowService.changeImage(slideShow);
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "ok");
        return map;
    }


}
