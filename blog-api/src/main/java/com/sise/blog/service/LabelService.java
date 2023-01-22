package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.blog.vo.TagVo;
import com.sise.common.pojo.Label;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.LabelVO;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:52
 */
public interface LabelService extends IService<Label> {
    List<LabelVO> findLabel();

    /**
     * 获取添加博客标签回显
     * @return
     */
    List<Label> getTagList();

    /**
     * 管理员后台标签数据
     * @param queryPageVO
     * @return
     */
    Page<LabelVO> getAdminTag(QueryPageVO queryPageVO);

    /**
     * 管理员后台删除标签
     * @param tagIdList
     */
    void delete(List<Integer> tagIdList);

    /**
     * 管理员后台标签添加或更改
     * @param label
     * @return
     */
    boolean saveOrUpdateTag(Label label);
}
