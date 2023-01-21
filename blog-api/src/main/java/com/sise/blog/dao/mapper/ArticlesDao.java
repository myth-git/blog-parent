package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.common.dto.BlogStatisticsDTO;
import com.sise.common.pojo.Articles;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.QueryPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 0:41
 */
@Repository
public interface ArticlesDao extends BaseMapper<Articles> {
    List<ArticlesVO> findHomePage(@Param("queryPageVO") QueryPageVO queryPageVO);

    List<ArticlesVO> findPersonBlog(@Param("queryPageVO") QueryPageVO queryPageVO, @Param("id") Long id);
    @Select("        SELECT DATE_FORMAT( create_time, \"%Y-%m-%d\" ) AS date, COUNT( 1 ) AS count\n" +
            "        FROM blog_articles " +
            "        GROUP BY date\n" +
            "        ORDER BY date DESC")
    /**
     * 文章统计
     */
    List<BlogStatisticsDTO> listArticleStatistics();

}
