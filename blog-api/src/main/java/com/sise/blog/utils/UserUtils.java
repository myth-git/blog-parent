package com.sise.blog.utils;

import com.sise.blog.dto.UserDetailDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Description: 获取当前用户登录的信息
 * @Author: xzw
 * @Date: 2022/12/31 21:22
 */
@Component
public class UserUtils {
    /*
    *  用户登录信息
    * */
    public static UserDetailDTO getLoginUser() {
        UserDetailDTO principal = (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

}
