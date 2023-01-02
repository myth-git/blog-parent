package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sise.blog.dao.mapper.RoleDao;
import com.sise.blog.dao.mapper.UserDao;
import com.sise.blog.dto.UserDetailDTO;
import com.sise.blog.utils.IpUtil;
import com.sise.common.constant.MessageConstant;
import com.sise.common.exception.BusinessException;
import com.sise.common.pojo.User;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Description: security进行表单验证
 * @Author: xzw
 * @Date: 2022/12/31 20:56
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RoleDao roleDao;

    /*
    * 返回接口所需的UserDetails实体
    *
    * */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //判断用户名是否为空
        if (StringUtils.isBlank(username)){
            throw new BusinessException(MessageConstant.USERNAME_IS_NULL);
        }
        //查询账号是否存在
        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,username));
        if (Objects.isNull(user)){
            throw new BusinessException(MessageConstant.USER_NOT_EXIST);
        }
        //封装登录信息
        return convertUserDetail(user, request);
    }

    private UserDetailDTO convertUserDetail(User user, HttpServletRequest request) {
        // 查询账号角色
        List<String> roleList = roleDao.listRolesByUid(user.getId());
        // 获取设备信息
        String ipAddress = IpUtil.getIpAddr(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roleList(roleList)
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .lastIp(ipAddress)
                .loginType(user.getLoginType())
                .ipSource(ipSource)
                .status(user.isStatus())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
