package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Comment;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 23:12
 */
@Repository
public interface CommentDao extends BaseMapper<Comment> {
}
