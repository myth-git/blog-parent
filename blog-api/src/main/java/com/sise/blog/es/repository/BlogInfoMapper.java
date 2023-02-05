package com.sise.blog.es.repository;

import com.sise.blog.es.index.BlogInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: es mapper，支持jpa规范
 * @Author: xzw
 * @Date: 2023/2/5 13:53
 */
@Repository
public interface BlogInfoMapper extends ElasticsearchRepository<BlogInfo, Long> {
}
