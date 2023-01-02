package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.blog.dto.UserDetailDTO;
import com.sise.common.pojo.User;

import javax.servlet.http.HttpServletRequest;

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
}
