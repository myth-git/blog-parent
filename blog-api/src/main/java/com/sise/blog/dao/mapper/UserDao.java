package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.dto.UserBackDTO;
import com.sise.common.pojo.User;
import com.sise.common.vo.QueryPageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 11:55
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    List<UserBackDTO> getAdminUserPage(@Param("queryPageVO") QueryPageVO queryPageVO);

}
