package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.TypeDao;
import com.sise.blog.service.TypeService;
import com.sise.common.pojo.Type;
import com.sise.common.vo.TypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:27
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public List<TypeVO> findType() {
        return typeDao.findType();
    }
}
