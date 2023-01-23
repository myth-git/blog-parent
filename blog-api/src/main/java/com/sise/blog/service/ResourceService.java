package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.LabelOptionDTO;
import com.sise.common.pojo.admin.Resource;
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
}
