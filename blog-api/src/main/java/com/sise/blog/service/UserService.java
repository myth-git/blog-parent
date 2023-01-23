package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.blog.dto.UserDetailDTO;
import com.sise.common.dto.UserBackDTO;
import com.sise.common.pojo.User;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.UserDisableVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 11:54
 */
public interface UserService extends IService<User> {
    /*
    * 用户注册
    * */
    boolean add(User user);
    /*
    * 查询用户是否存在
    * */
    boolean existUser(String username);
    /*
    * 检验验证码
    * */
    boolean verifyCode(String verKey, String code);
    /*
    * 获取用户信息
    * */
    UserDetailDTO getUserDetail(String username, HttpServletRequest request, String ipAddress, String ipSource);
    /*
    * 根据id查询用户
    * */
    User findById(long userId);
    /*
     * 获取用户列表
     * */
    List<User> getUserList();

    /**
     * 获取管理员后台用户列表
     * @param queryPageVO
     * @return
     */
    Page<UserBackDTO> getAdminUser(QueryPageVO queryPageVO);

    /**
     * 修改用户禁用状态
     * @param userDisableVO
     */
    void updateUserDisable(UserDisableVO userDisableVO);
}
