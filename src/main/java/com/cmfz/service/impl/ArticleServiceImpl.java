package com.cmfz.service.impl;

import com.cmfz.dao.ArticleDao;
import com.cmfz.dao.ArticleElastCRUD;
import com.cmfz.entity.Article;
import com.cmfz.entity.ArticleElast;
import com.cmfz.service.ArticleService;
import com.cmfz.vo.Day7VO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @className: ArticleServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-25 20:12
 * @Version 1.0
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS)
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleElastCRUD articleElastCRUD;


    @Override
    public List<Article> queryArticle(Article article) {
        return articleDao.select(article);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void changeArticle(Article article) {

        articleDao.update(article);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removerArticle(String[] ids, String author_id) {
        articleDao.delete(ids, author_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removerArticleByids(String[] ids) {
        articleDao.deleteByids(ids);
        deleteEsContent(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removerByAuthorId(String author_id) {
        articleDao.deleteByAuthorId(author_id);
    }

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

        int total = recoreds % 10 == 0 ? recoreds / 10 : recoreds / 10 + 1;

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);

        map.put("page", start);

        map.put("records", recoreds);

        start = (start - 1) * 10;

        map.put("rows", articleDao.selectPage(start));
        return map;
    }

    @Override
    public int queryTotalNumber() {
        return articleDao.selectTotalNumber();
    }

    @Override
    public int queryTotalByAuthor(String author_id) {
        return articleDao.selectTotalByAuthor(author_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addArticle(Article article, String content) {
        //随机ID
        String uuid = UUID.randomUUID().toString();

        article.setId(uuid);

        articleDao.insert(article);
        //Es添加

        //常见文档
        ArticleElast articleElast = new ArticleElast();

        articleElast.setId(uuid);


        //设置内容
        articleElast.setContent(content);

        //添加文档
        articleAddtoes(articleElast);


    }

    //ES添加文档
    public void articleAddtoes(ArticleElast articleElast) {
        articleElastCRUD.save(articleElast);
    }

    //查询ES文章内容

    public Map<String, Object> viewArticleContent(String id) {
        Map<String, Object> map = getMap();
        if (id != null) {


            ArticleElast elast = articleElastCRUD.findById(id).get();


            map.put("id", elast.getId());
            map.put("content", elast.getContent());


            return map;
        }
        map.put("status", -1);
        return map;
    }
    //删除ES文章内容

    public void deleteEsContent(String[] articleId) {
        //文档信息
        ArrayList<ArticleElast> articleElasts = new ArrayList<>();
        for (String s : articleId) {
            ArticleElast articleElast = new ArticleElast();

            articleElast.setId(s);

            articleElasts.add(articleElast);

            log.info(s);
        }
        //删除文档articleIds为 articleIds

        articleElastCRUD.deleteAll(articleElasts);

    }

    //*********************文件*********************

    //文件上传功能
    /*
     * --文件到服务器->拷贝文件到服务器哪个目录-->返回信息
     *
     * */

    public Map<String, Object> fileCopy(HttpServletRequest request, MultipartFile multipartFile) {

        //获取文章图片的路径
        String articleImage = getServerDirPath(request, "resources");

        String fileName = new Date().getTime() + "_" + multipartFile.getOriginalFilename();

        try {
            //将上传的文件复制到服务器文件夹里 完成文件上传

            multipartFile.transferTo(new File(articleImage + "/articleImage", fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //打印出来


        String serverAddress = getServerAddress(request);

        HashMap<String, Object> map = (HashMap<String, Object>) getMap();

        map.put("error", 0);
        map.put("url", serverAddress + "/resources/articleImage/" + fileName);
        return map;
    }

    //获取服务器地址
    public String getServerAddress(HttpServletRequest request) {
        //服务器端口
        int port = request.getServerPort();
        //项目名
        String contextPath = request.getContextPath();
        //服务器路径
        String servletPath = request.getServletPath();
        //获取协议
        String scheme = request.getScheme();
        //http协议
        String deal = scheme.toString();
        //获取ip
        String serverName = request.getServerName();
        return deal + "://" + serverName + ":" + port + contextPath;
    }

    //文件遍历功能
   /* /var/www / kindsoft / ke4 / attached {
        "moveup_dir_path": "",
                "current_dir_path": "",
                "current_url": "\/ke4\/php\/..\/attached\/",
                "total_count": 5,
                "file_list": [{
            "is_dir": false,
                    "has_file": false,
                    "filesize": 208736,
                    "dir_path": "",
                    "is_photo": true,
                    "filetype": "jpg",
                    "filename": "1241601537255682809.jpg",
                    "datetime": "2018-06-06 00:36:39"
        }]*/

    public Map<String, Object> getFileList(HttpServletRequest request) {
        //将所有图片信息返回  不需要参数

        //获取服务器路径
        String dirPath = getServerDirPath(request, "resources");

        //遍历文件夹文件

        File file = new File(dirPath, "\\articleImage");

        //获取文件夹下所有文件
        File[] files = file.listFiles();
        List<Object> datas = new ArrayList<>();
        //遍历文件
        for (File fil : files) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("is_dir", false);
            hashMap.put("has_file", false);
            hashMap.put("filesize", fil.length());
            hashMap.put("dir_path", "");
            hashMap.put("is_photo", "true");
            //打印文件名

            String name = fil.getName();

            String substring = name.substring(name.lastIndexOf(".") + 1, name.length());

            hashMap.put("filetype", substring);

            hashMap.put("filename", fil.getName());

            hashMap.put("datetime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(Long.valueOf(fil.getName().split("_")[0]))));


            datas.add(hashMap);


        }
        HashMap<String, Object> map = new HashMap<>();

        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        //图片空间前缀
        map.put("current_url", "/cmfz/resources/articleImage/");
        map.put("total_count", datas.size());
        map.put("file_list", datas);
        return map;
    }

    //获取服务器图片路径

    public String getServerDirPath(HttpServletRequest request, String dirName) {
        String realPath = request.getServletContext().getRealPath(dirName);
        return realPath;
    }


    //获取Map

    public Map<String, Object> getMap() {
        return new HashMap<>();
    }


    // 获取最近七天文章发布数


    public List<Day7VO> getDay7Article() {
        return articleDao.selectDay7();
    }


}
