package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Label;
import com.sise.common.vo.LabelVO;
import com.sise.common.vo.QueryPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:53
 */
@Repository
public interface LabelDao extends BaseMapper<Label> {
    List<LabelVO> findLabel();

    List<Label> getLabelNameList(@Param("id") Long id);

    /**
     * 获取管理员后台标签的分页数据
     * @param queryPageVO
     * @return
     */
    List<LabelVO> getAdminTag(@Param("queryPageVO") QueryPageVO queryPageVO);
}
