package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesLabelDao;
import com.sise.blog.dao.mapper.LabelDao;
import com.sise.blog.service.LabelService;
import com.sise.common.exception.BusinessException;
import com.sise.common.pojo.ArticlesLabel;
import com.sise.common.pojo.Label;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.LabelVO;
import com.sise.common.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public Page<LabelVO> getAdminTag(QueryPageVO queryPageVO) {
        Page<LabelVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, "label_name", queryPageVO.getQueryString());
        page.setTotal(labelDao.selectCount(queryWrapper));
        page.setRecords(labelDao.getAdminTag(queryPageVO));
        return page;
    }

    @Override
    public void delete(List<Integer> tagIdList) {
        Integer count = articlesLabelDao.selectCount(new LambdaQueryWrapper<ArticlesLabel>()
                .in(ArticlesLabel::getLabelId, tagIdList));
        if (count > 0) {
            throw new BusinessException("标签下有文章，不能删除");
        }
        labelDao.deleteBatchIds(tagIdList);
    }

    @Override
    public boolean saveOrUpdateTag(Label label) {
        Label selectOne = labelDao.selectOne(new LambdaQueryWrapper<Label>().eq(Label::getLabelName, label.getLabelName())
                .select(Label::getId));
        if (Objects.nonNull(selectOne)) {
            throw new BusinessException("标签已存在");
        }
        Label label1 = Label.builder()
                .id(label.getId())
                .labelName(label.getLabelName())
                .build();
        this.saveOrUpdate(label1);
        return true;
    }
}
