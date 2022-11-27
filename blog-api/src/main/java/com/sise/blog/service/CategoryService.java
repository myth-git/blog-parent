package com.sise.blog.service;

import com.sise.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
