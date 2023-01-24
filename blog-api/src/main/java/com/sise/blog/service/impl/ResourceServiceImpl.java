package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.sise.blog.dao.mapper.ResourceDao;
import com.sise.blog.dao.mapper.RoleResourceDao;
import com.sise.blog.service.ResourceService;
import com.sise.blog.utils.BeanCopyUtils;
import com.sise.common.dto.LabelOptionDTO;
import com.sise.common.dto.ResourceDTO;
import com.sise.common.pojo.admin.Resource;
import com.sise.common.pojo.admin.RoleResource;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.ResourceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private RoleResourceDao roleResourceDao;

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

    @Override
    public List<ResourceDTO> listResources(QueryPageVO queryPageVO) {
        //查出所有资源
        List<Resource> resourceList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .like(StringUtils.isNotEmpty(queryPageVO.getQueryString()), Resource::getResourceName, queryPageVO.getQueryString()));
        //查出父资源
        List<Resource> parentList = listResourceModule(resourceList);
        //查出父id对应的子资源
        Map<Integer, List<Resource>> childrenList = listResourceChildren(resourceList);
        //父子资源组合
        List<ResourceDTO> resourceDTOList = parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtils.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenResourceList = BeanCopyUtils.copyList(childrenList.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenResourceList);
            return resourceDTO;
        }).collect(Collectors.toList());

        // 拼接未取出的资源
//        if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(childrenList)) {
//            List<Resource> childrenList1 = new ArrayList<>();
//            childrenList.values().forEach(childrenList1::addAll);
//            List<ResourceDTO> childrenDTOList = childrenList1.stream()
//                    .map(item -> BeanCopyUtils.copyObject(item, ResourceDTO.class))
//                    .collect(Collectors.toList());
//            resourceDTOList.addAll(childrenDTOList);
//        }

        return resourceDTOList;
    }

    @Override
    public void saveOrUpdateResource(ResourceVO resourceVO) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceVO,resource);
        if (resourceVO.getId() != null) {
            resource.setUpdateTime(LocalDateTime.now());
        }
        this.saveOrUpdate(resource);
    }

    @Override
    public void deleteResource(Integer resourceId) {
        //删除子资源id
        List<Integer> childrenList = resourceDao.selectList(new LambdaQueryWrapper<Resource>()
                .eq(Resource::getParentId, resourceId)
                .select(Resource::getId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        //删除父资源id,把父资源id放入到集合中
        childrenList.add(resourceId);
        resourceDao.deleteBatchIds(childrenList);
        roleResourceDao.delete(new LambdaQueryWrapper<RoleResource>().eq(RoleResource::getResourceId, resourceId));
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
