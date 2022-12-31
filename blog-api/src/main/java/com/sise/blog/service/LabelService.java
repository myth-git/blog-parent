package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Label;
import com.sise.common.vo.LabelVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:52
 */
public interface LabelService extends IService<Label> {
    List<LabelVO> findLabel();
}
