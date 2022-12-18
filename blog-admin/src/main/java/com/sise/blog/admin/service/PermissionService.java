package com.sise.blog.admin.service;

import com.sise.blog.admin.model.params.PageParam;
import com.sise.blog.admin.pojo.Permission;
import com.sise.blog.admin.vo.Result;

public interface PermissionService {
    /*
    * 分页查询权限列表
    * */
    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
