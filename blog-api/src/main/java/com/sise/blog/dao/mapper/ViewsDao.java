package com.sise.blog.dao.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.dto.ViewsDTO;
import com.sise.common.pojo.Views;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/19 11:44
 */
@Repository
public interface ViewsDao extends BaseMapper<Views> {

    List<ViewsDTO> getViewsData(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
