package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.LabelOptionDTO;
import com.sise.common.dto.ResourceDTO;
import com.sise.common.pojo.admin.Resource;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.ResourceVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/6 0:08
 */
public interface ResourceService extends IService<Resource> {
    /**
     * 查看用户的所有资源
     * @return
     */
    List<String> getResourceList();

    /**
     * 根据用户id查询对应的权限资源
     * @param id
     * @return
     */
    List<String> getUserResource(Long id);

    /**
     * 角色管理查看角色菜单选项
     * @return
     */
    List<LabelOptionDTO> listResourceOptions();

    /**
     * 接口管理查看资源列表
     * @param queryPageVO
     * @return
     */
    List<ResourceDTO> listResources(QueryPageVO queryPageVO);

    /**
     * 接口管理新增或修改资源
     * @param resourceVO
     */
    void saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 接口管理删除资源
     * @param resourceId
     */
    void deleteResource(Integer resourceId);
}
