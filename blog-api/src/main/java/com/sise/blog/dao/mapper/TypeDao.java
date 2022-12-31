package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.Type;
import com.sise.common.vo.TypeVO;
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
}
