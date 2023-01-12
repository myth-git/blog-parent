package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ResourceDao;
import com.sise.blog.service.ResourceService;
import com.sise.common.pojo.admin.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/6 0:08
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @javax.annotation.Resource
    private ResourceDao resourceDao;

    @Override
    public List<String> getResourceList() {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("id",275);
        List<Resource> resourceList = resourceDao.selectList(queryWrapper);
        List<String> resultList = resourceList.stream().map(t -> t.getRequestMethod() + t.getUrl()).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public List<String> getUserResource(Long id) {
        return resourceDao.getUserResource(id);
    }
}
