package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Type;
import com.sise.common.vo.TypeVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:26
 */
public interface TypeService extends IService<Type> {
    List<TypeVO> findType();
}
