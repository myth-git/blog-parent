package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Label;
import com.sise.common.vo.LabelVO;
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
}
