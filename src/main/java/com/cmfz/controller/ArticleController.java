package com.cmfz.controller;

import com.cmfz.entity.Article;
import com.cmfz.entity.ArticleElast;
import com.cmfz.entity.Option;
import com.cmfz.service.ArticleService;
import com.cmfz.service.impl.ArticleServiceImpl;
import com.cmfz.vo.Day7VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ArticleController
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-25 20:17
 * @Version 1.0
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired

    private ArticleService articleService;


    //修改文章
    @RequestMapping("/changeArticle")
    public Map<String, Object> changeArticle(Article article) {

        articleService.changeArticle(article);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg", 2000);
        return hashMap;
    }

    //分页查询
    @RequestMapping("/queryArticlePage")
    public Map<String, Object> queryArticlePage(Option option) {
        System.out.println(option);
        Map<String, Object> map = articleService.queryPage(option.getPage());

        return map;
    }

    //修改文章内容
    @RequestMapping("/changeArticleContent")
    public Map<String, Object> changeArticleContent(String articleId, String content) {
        //创建对象
        ArticleElast articleElast = new ArticleElast();
        //设置ID
        articleElast.setId(articleId);
        //设置内容
        articleElast.setContent(content);
        //更新文档
        ((ArticleServiceImpl) articleService).articleAddtoes(articleElast);

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);
        return map;
    }

    //删除文章
    @RequestMapping("/removeArticle")
    public Map<String, Object> removeArticle(String[] ids) {

        articleService.removerArticleByids(ids);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg", 2000);
        return hashMap;
    }

    //编辑操作
    @RequestMapping("/optionedit")
    public Map<String, Object> optionsEdit(Option option, Article article) {
        if (option.getOper().equals("del")) {

            articleService.removerArticleByids(option.getId());
        }

        if (option.getOper().equals("edit")) {
            //修改文章信息
            articleService.changeArticle(article);
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg", "编辑操作");

        return hashMap;
    }

    //图片上传

    /**
     * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
     */

    /**
     * @return 返回上传完成的地址和状态
     */

    @RequestMapping("/imageUpload")
    public Map<String, Object> imageUpload(HttpServletRequest request, MultipartFile fileupload) {
        System.out.println(fileupload.getSize());

        Map<String, Object> map = ((ArticleServiceImpl) articleService).fileCopy(request, fileupload);

        return map;

    }


    //查看图片空间

    @RequestMapping("/imageView")

    public Map<String, Object> imageView(HttpServletRequest request) {
        return ((ArticleServiceImpl) articleService).getFileList(request);
    }

    //保存文章

    @RequestMapping("/save")

    public Map<String, Object> saveArticle(Article article, String content) {
        //文章信息

        //内容


        articleService.addArticle(article, content);
        return null;
    }

    //预览文章内容


    @GetMapping("/viewArticle")
    public Map<String, Object> viewArticleContent(String articleId) {

        Map<String, Object> map = ((ArticleServiceImpl) articleService).viewArticleContent(articleId);

        return map;
    }

    //测试接口

    @RequestMapping("/test")
    public List<Day7VO> test() {
        //获取星期1-7集合
        List<Day7VO> day7Article = ((ArticleServiceImpl) articleService).getDay7Article();

        return day7Article;
    }


}
