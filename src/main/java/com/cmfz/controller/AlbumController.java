package com.cmfz.controller;

import com.cmfz.entity.Album;
import com.cmfz.entity.Option;
import com.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: AlbumController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-24 16:09
 * @Version 1.0
 */

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired

    private AlbumService albumService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/selectPage")
    //分页查询
    public Map<String, Object> queryPage(Option option) {

        System.out.println(option);

        Map<String, Object> map = albumService.queryPage(option.getPage());

        return map;
    }

    @RequestMapping("/optionedit")
    //专辑 增 删 改
    public Map<String, Object> OptionEdit(Option option) {

        if (option.getOper().equals("del")) {
            //删除操作

            //根据ids批量删除专辑

            albumService.removerAlbum(option.getId());

            HashMap<String, Object> map = getMapObject();

            map.put("msg", "删除操作");

            map.put("ops", option);
            return map;
        }

        return null;


    }

    @RequestMapping("/albumImgUpload")

    //专辑封面上传
    public Map<String, Object> albumImgUpload(@RequestParam("album_Img_Path1") MultipartFile multipartFile, Album album) {
        //获取状态信息容器
        HashMap<String, Object> map = getMapObject();
        //拷贝文件返回文件名
        String fileCopy = fileCopy(multipartFile);
        //设置id
        album.setId(UUID.randomUUID().toString());
        //设置路径
        album.setAlbum_Img_Path("/albumImages/" + fileCopy);

        System.out.println(album);

        //插入数据库信息

        albumService.addAlbum(album);

        map.put("msg", "封面上传");

        map.put("fileName", fileCopy);

        return map;
    }

    @RequestMapping("/createAlbum")
    //创建专辑接口
    public Map<String, Object> createAlbum(Album album) {
        System.out.println(album);
        albumService.addAlbum(album);

        HashMap<String, Object> map = getMapObject();
        System.out.println(album);
        map.put("msg", "创建专辑");
        map.put("album", album);

        return map;
    }

    @RequestMapping("/removerAlbum")
    //删除专辑接口
    public Map<String, Object> removerAlbum(String[] albumids) {

        albumService.removerAlbum(albumids);

        HashMap<String, Object> map = getMapObject();
        map.put("msg", "删除专辑");

        map.put("albumids", albumids);

        return map;
    }

    @RequestMapping("/updateAlbum")
    //更新专辑接口
    public Map<String, Object> updateAlbum(@RequestParam("album_Img_Path1") MultipartFile multipartFile, Album album) {

        //判断有没有修改文件
        if (multipartFile != null && multipartFile.getSize() > 0) {
            //有文件

            Album album1 = new Album();
            //设置id
            album1.setId(album.getId());
            //根据id查对象
            List<Album> albums = albumService.queryAlbum(album1);
            //获得服务器路径
            String path = albums.get(0).getAlbum_Img_Path();
            //删除源文件
            //正则取得文件名
            Pattern compile = Pattern.compile("\\d*_.*\\.png");

            Matcher matcher = compile.matcher(path);

            matcher.find();

            String group = matcher.group();
            System.out.println(group);
            deleteFile(group);
            //拷贝新文件
            String fileCopy = fileCopy(multipartFile);

            //修改数据库文件路径
            album.setAlbum_Img_Path("/albumImages/" + fileCopy);

            albumService.changeAlbum(album);

        } else {
            //没有文件
            System.out.println(album);
            albumService.changeAlbum(album);
        }


        HashMap<String, Object> map = getMapObject();
        map.put("msg", "更新专辑");

        map.put("album", album);
        return map;
    }


    public HashMap<String, Object> getMapObject() {
        return new HashMap<>();
    }

    //文件拷贝

    public String fileCopy(MultipartFile file) {
        //取得文件名

        String filename = new Date().getTime() + "_" + file.getOriginalFilename();

        //获取服务器路径

        //拼接完成 H:\MyelipseWorkspace\cmfz\src\main\webapp\resources\albumImages\1页.png

        String imagePath = getFilePaht("resources") + filename;

        try {
            //拷贝文件
            file.transferTo(new File(imagePath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件名
        return filename;
    }

    //取得服务器文件夹路径
    public String getFilePaht(String mkdirName) {
        //取得服务器文件夹路径
        String realPath = request.getServletContext().getRealPath(mkdirName);

        System.out.println(realPath);

        return realPath + "\\albumImages\\";
    }

    //文件删除
    public String deleteFile(String filename) {

        String imagePath = getFilePaht("resources") + filename;

        try {
            Files.delete(new File(imagePath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回文件名
        return filename;

    }

    //测试
    @RequestMapping("/test")
    public void test() {

        albumService.queryYear();

    }

    //获取专辑发布数量
    @GetMapping("/getOneYear")
    public Map<String, Object> getOneYearNumber() {

        List<Integer> integers = albumService.queryYear();
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("album", integers);

        return hashMap;
    }

}
