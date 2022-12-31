package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.LabelDao;
import com.sise.blog.service.LabelService;
import com.sise.common.pojo.Label;
import com.sise.common.vo.LabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:52
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelDao,Label> implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<LabelVO> findLabel() {
        return labelDao.findLabel();
    }
}
