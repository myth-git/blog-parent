package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Comment;
import com.sise.common.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 23:12
 */
@Repository
public interface CommentDao extends BaseMapper<Comment> {

    List<CommentVO> getRootList(Long id);

    List<CommentVO> getChildList(Long id);

}
