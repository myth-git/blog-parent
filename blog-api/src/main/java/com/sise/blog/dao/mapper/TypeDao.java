package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Type;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.TypeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:28
 */
@Repository
public interface TypeDao extends BaseMapper<Type> {
    List<TypeVO> findType();

    /**
     * 获取后台管理分页数据
     * @param queryPageVO
     * @return
     */
    List<TypeVO> getAdminType(@Param("queryPageVO") QueryPageVO queryPageVO);
}
