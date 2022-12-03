package com.sise.blog.service;

import com.sise.blog.vo.CategoryVo;
import com.sise.blog.vo.Result;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 写文章--展示所有分类
     * @return
     */
    Result findAll();

    /**
     * 导航--文章分类
     * @return
     */
    Result findAllDetail();

    /**
     * 根据id查询对应文章分类
     * @param id
     * @return
     */
    Result categoryDetailById(Long id);
}
