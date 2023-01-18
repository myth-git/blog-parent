package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
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

}
