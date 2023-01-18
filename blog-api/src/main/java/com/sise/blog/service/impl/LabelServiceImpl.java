package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesLabelDao;
import com.sise.blog.dao.mapper.LabelDao;
import com.sise.blog.service.LabelService;
import com.sise.common.pojo.ArticlesLabel;
import com.sise.common.pojo.Label;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.LabelVO;
import com.sise.common.vo.QueryPageVO;
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
    @Autowired
    private ArticlesLabelDao articlesLabelDao;

    @Override
    public List<LabelVO> findLabel() {
        return labelDao.findLabel();
    }

    @Override
    public List<Label> getTagList() {
        List<Label> labelList = labelDao.selectList(null);
        return labelList;
    }


}
