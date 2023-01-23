package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.dto.RoleDTO;
import com.sise.common.pojo.admin.Role;
import com.sise.common.vo.QueryPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 角色接口
 * @Author: xzw
 * @Date: 2023/1/1 16:03
 */
@Repository
public interface RoleDao extends BaseMapper<Role> {
    /*
    * 根据用户id查询对应的角色
    * */
    List<String> listRolesByUid(@Param("id") Long id);

    /**
     * 获取角色列表和当前角色拥有的resource和menu
     * @param queryPageVO
     * @return
     */
    List<RoleDTO> listRole(@Param("queryPageVO") QueryPageVO queryPageVO);
}
