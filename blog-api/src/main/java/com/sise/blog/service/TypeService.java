package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Type;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.TypeVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:26
 */
public interface TypeService extends IService<Type> {
    List<TypeVO> findType();

    /**
     * 获取添加博客列表渲染
     * @return
     */
    List<Type> getTypeList();

    /**
     * 获取后台分类分页数据
     * @param queryPageVO
     * @return
     */
    Page<TypeVO> getAdminType(QueryPageVO queryPageVO);

    /**
     * 管理员后台删除分类
     * @param typeIdList
     */
    void delete(List<Integer> typeIdList);

    /**
     * 管理员后台分类添加或更改
     * @param type
     * @return
     */
    boolean saveOrUpdateType(Type type);
}
