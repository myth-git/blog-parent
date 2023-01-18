package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.ArticlesLabel;
import com.sise.common.vo.ArticlesVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/13 0:42
 */
@Repository
public interface ArticlesLabelDao extends BaseMapper<ArticlesLabel> {
    /**
     * 根据标签类型获取博客列表
     * @param start
     * @param pageSize
     * @return
     */
    @Select("SELECT ba.id, bu.nickname, bu.avatar, bt.type_name, bl.label_name, ba.views, ba.description, ba.create_time ,ba.recommend, ba.published, ba.update_time, ba.title, ba.first_picture " +
            "FROM blog_articles ba, blog_user bu, blog_label bl, blog_articles_label bal, blog_type bt " +
            "WHERE ba.user_id = bu.id AND ba.id = bal.articles_id AND bal.label_id = bl.id AND bl.id = #{labelId} AND bt.id = ba.type_id " +
            "GROUP BY ba.id " +
            "ORDER BY ba.views DESC " +
            "LIMIT #{start},#{pageSize}")
    List<ArticlesVO> getByTagId(Integer start, Integer pageSize, Integer labelId);
}
