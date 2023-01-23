package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.sise.blog.dao.mapper.ResourceDao;
import com.sise.blog.service.ResourceService;
import com.sise.common.dto.LabelOptionDTO;
import com.sise.common.pojo.admin.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Override
    public List<LabelOptionDTO> listResourceOptions() {
        //查询所有的资源列表
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, 0));
        //获取父资源列表
        List<Resource> parentResourceList = listResourceModule(resourceList);
        //获取根据父id的子资源列表
        Map<Integer, List<Resource>> childrenListMap = listResourceChildren(resourceList);
        List<LabelOptionDTO> collect = parentResourceList.stream().map(t -> {
            //用来放children的
            List<LabelOptionDTO> labelOptionDTOList = new ArrayList<>();
            List<Resource> childrenList = childrenListMap.get(t.getId());
            if (CollectionUtils.isNotEmpty(childrenList)) {
                labelOptionDTOList = childrenList.stream()
                        .map(j -> LabelOptionDTO.builder()
                                .id(j.getId())
                                .label(j.getResourceName())
                                .build()).collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(t.getId())
                    .label(t.getResourceName())
                    .children(labelOptionDTOList)
                    .build();
        }).collect(Collectors.toList());
        return collect;
    }

    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                           .filter(t -> Objects.nonNull(t.getParentId()))
                           .collect(Collectors.groupingBy(Resource::getParentId));
    }

    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                           .filter(t -> Objects.isNull(t.getParentId()))
                           .collect(Collectors.toList());
    }
}
