package com.cmfz.controller;

import com.cmfz.entity.AlbumAudio;
import com.cmfz.entity.Option;
import com.cmfz.service.AlbumAudioService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className: AlbumAudioController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-24 16:09
 * @Version 1.0
 */

@RestController
@RequestMapping("/albumAudio")
public class AlbumAudioController {
    @Autowired
    private AlbumAudioService albumAudioService;
    @Autowired
    private HttpServletRequest request;

    //章节增
    @RequestMapping("/addAlbumAudio")
    public Map<String, Object> addAlbumAudio(AlbumAudio albumAudio) {
        System.out.println(albumAudio);
        albumAudioService.addAlbumAudio(albumAudio);

        HashMap<String, Object> map = getMapObject();

        map.put("msg", "章节增");

        map.put("addAlbumAudio", albumAudio);

        return map;

    }

    //音频文件上传
    @RequestMapping("/albumMp3Upload")
    public Map<String, Object> albumMp3Upload(@RequestParam("albumMp3") MultipartFile multipartFile, AlbumAudio albumAudio) {

        HashMap<String, Object> map = getMapObject();

        String fileCopy = fileCopy(multipartFile);

        //获取上传完的文件

        String resourcesPath = getFilePaht("resources");
        System.out.println(resourcesPath + fileCopy);

        File file = new File(resourcesPath + fileCopy);
        Map<String, String> map1 = getSizeTimelong(file);
        //设置id
        albumAudio.setId(UUID.randomUUID().toString());
        //设置路径
        albumAudio.setAudio_Url("/mp3/" + fileCopy);
        //设置时长
        albumAudio.setAudio_Timelong(map1.get("timelong"));
        //设置大小
        albumAudio.setAudio_Size(map1.get("filesize"));


        //上传数据库

        albumAudioService.addAlbumAudio(albumAudio);

        map.put("msg", "音频文件上传");

        map.put("fileName", fileCopy);

        return map;
    }

    //获取音频时长 大小
    public Map<String, String> getSizeTimelong(File file) {

        try {
            //创建解码器对象

            //读入文件返回音频文件对象
            AudioFile read = AudioFileIO.read(file);
            //获取音频头信息
            AudioHeader audioHeader = read.getAudioHeader();


            int trackLength = audioHeader.getTrackLength();
            int seconds = trackLength % 60;

            int min = trackLength / 60;
            Map<String, String> map = new HashMap<>();
            //保存时长
            map.put("timelong", min + ":" + seconds);
            //计算文件大小
            //字节
            int length = (int) file.length();

            //1kb=1024字节

            int a = length / 1024;
            //保存大小
            map.put("filesize", (a * 100 / 1024) / 100.0 + "mb");

            return map;
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return null;

    }

    //章节删
    @RequestMapping("/deleteAlbumAudio")
    public Map<String, Object> deleteAlbumAudio(String[] albumAudios) {

        albumAudioService.removerAlbumAudio(albumAudios);

        HashMap<String, Object> map = getMapObject();

        map.put("msg", "章节删");

        map.put("deleteAlbumAudio", albumAudios);

        return map;

    }

    //章节改
    @RequestMapping("/updateAlbumAudio")
    public Map<String, Object> updateAlbumAudio(@RequestParam("albumMp3") MultipartFile multipartFile, AlbumAudio albumAudio) {

        //修改
        //有文件 删除原先文件 拷贝新文件

        //设置url
        if (albumAudio.getAudio_Url().equals("undefined")) {
            //undefined
            albumAudio.setAudio_Url(null);
        }
        if (multipartFile != null && multipartFile.getSize() > 0) {
            //有文件

            //删除文件 拷贝新文件  修改数据库
            AlbumAudio albumAudio1 = new AlbumAudio();

            //查出路径
            List<AlbumAudio> audios = albumAudioService.queryByIds(new String[]{albumAudio.getId()});

            System.out.println(audios);

            //文件路径
            String audio_url = audios.get(0).getAudio_Url();
            //删除
            deleteFile(audio_url.substring(audio_url.lastIndexOf("/mp3/") + 5, audio_url.length()));

            //写新文件
            String fileCopy = fileCopy(multipartFile);
            //设置URL
            albumAudio.setAudio_Url("/mp3/" + fileCopy);

            albumAudioService.changeAlbumAudio(albumAudio);


        } else {
            //没有文件 只修改数据库

            albumAudioService.changeAlbumAudio(albumAudio);
        }


        HashMap<String, Object> map = getMapObject();

        map.put("msg", "章节改");

        map.put("updateAlbumAudio", albumAudio);

        return map;

    }

    //章节分页查
    @RequestMapping("/pageAlbumAudio")
    public Map<String, Object> pageAlbumAudio(Option option, String albumId) {


        System.out.println(albumId);

        Map<String, Object> map = albumAudioService.queryPage(option.getPage(), albumId);


        return map;

    }
    //修改操作

    @RequestMapping("/optionEdit")
    public Map<String, Object> Optionedits(Option option) {

        if (option.getOper().equals("del")) {
            //删除
            //查出所有id值
            String[] ids = option.getId();
            List<AlbumAudio> audios = albumAudioService.queryByIds(ids);

            for (AlbumAudio audio : audios) {
                //获取路径
                String audio_url = audio.getAudio_Url();

                //删除文件
                if (audio_url.contains("mp3")) {
                    //存在mp3文件 删除
                    deleteFile(audio_url.substring(audio_url.lastIndexOf("/mp3/") + 5, audio_url.length()));
                }
            }
            //删除数据库数据
            deleteAlbumAudio(ids);


        }

        if (option.getOper().equals("edit")) {


            return null;
        }

        System.out.println(option);
        return null;
    }

    //获取Map
    public HashMap<String, Object> getMapObject() {
        return new HashMap<>();
    }

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


        return realPath + "\\mp3\\";
    }

    //文件删除
    @RequestMapping("/deleteFile")
    public String deleteFile(String filename) {

        String imagePath = getFilePaht("resources") + filename;

        try {
            boolean exists = new File(imagePath).exists();
            if (exists) {
                Files.delete(new File(imagePath).toPath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回文件名
        return filename;

    }

    //音频下载
    @RequestMapping("/albumAudioDownLoad")
    @ResponseBody
    public void albumAudioDownLoad(String filePath, HttpServletResponse response) {

        //响应头response.setHeader("ContentType-Disposition”,"");

        /*/mp3/1577201872539_04.mp3*/

        Pattern compile = Pattern.compile("\\d*_.*\\.mp3");
        Matcher matcher = compile.matcher(filePath);

        matcher.find();
        String group = matcher.group();
        System.out.println(group);
        String encode = null;
        try {
            encode = URLEncoder.encode(group, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //取得后缀


        response.setHeader("Content-Disposition", "attachment; filename=" + encode);
        //响应类型
        response.setContentType("application/x-msdownload;charset=utf-8");
        //文件流读

        try {

            File file = new File(getFilePaht("resources") + group);
            //缓冲区大小
            byte[] bytes = new byte[(int) file.length()];

            FileInputStream inputStream = new FileInputStream(file);

            //读入数据
            inputStream.read(bytes);

            inputStream.close();

            ServletOutputStream outputStream = response.getOutputStream();

            //流写出文件
            outputStream.write(bytes);

            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
