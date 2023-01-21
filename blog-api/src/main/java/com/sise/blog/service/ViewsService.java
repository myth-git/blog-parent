package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.ViewsDTO;
import com.sise.common.pojo.Views;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/19 11:43
 */
public interface ViewsService extends IService<Views> {
    /**
     *  查询一周访问量
     * @return
     */
    List<ViewsDTO> getViewsData();

}
