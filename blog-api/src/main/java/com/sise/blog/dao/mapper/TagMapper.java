package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查找最热标签前6个
     * @param limit
     * @return
     */
    List<Long> findHostTagIds(int limit);

    List<Tag> findTagsTagIds(List<Long> tagIds);

}
