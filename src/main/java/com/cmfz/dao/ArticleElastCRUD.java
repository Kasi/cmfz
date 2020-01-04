package com.cmfz.dao;

import com.cmfz.entity.ArticleElast;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-26 14:55
 * @Description TODO
 */

@Repository
public interface ArticleElastCRUD extends ElasticsearchRepository<ArticleElast, String> {

}
