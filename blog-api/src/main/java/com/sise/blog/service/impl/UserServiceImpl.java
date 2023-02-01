package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.RoleDao;
import com.sise.blog.dao.mapper.UserDao;
import com.sise.blog.dao.mapper.UserRoleDao;
import com.sise.blog.dto.UserDetailDTO;
import com.sise.blog.service.UserService;
import com.sise.blog.utils.IpUtil;
import com.sise.blog.utils.RedisUtil;
import com.sise.common.constant.MessageConstant;
import com.sise.common.constant.RedisConst;
import com.sise.common.dto.ResetPasswordDTO;
import com.sise.common.dto.UserBackDTO;
import com.sise.common.exception.BusinessException;
import com.sise.common.pojo.User;
import com.sise.common.pojo.admin.UserRole;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.UserDisableVO;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 11:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    @Transactional//声明式事务，这里使用声明式事务可能有两个地方使用user对象
    public boolean add(User user) {
        if (userService.existUser(user.getUsername())) {
            return false;
        }
        long id = IdWorker.getId(User.class);
        user.setId(id);
        user.setAvatar(isImagesTrue(user.getAvatar()));//头像无效转成自动头像
        user.setStatus(MessageConstant.USER_ABLE);
        user.setPassword(encoder.encode(user.getPassword()));//采用security进行密码加密
        userDao.insert(user);
        //给用户赋予角色
        UserRole userRole = new UserRole();
        userRole.setRid(2);//表示普通用户
        userRole.setUid(user.getId());
        userRoleDao.insert(userRole);
        return true;
    }

    @Override
    public boolean existUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id")
                .eq("username", username)
                .last("limit 0,1");
        return userDao.selectCount(queryWrapper) != 0;
    }

    @Override
    public boolean verifyCode(String verKey, String code) {
        //redis容器中获取真正的验证码
        String realCode = (String) redisUtil.get(RedisConst.USER_CODE_KEY + verKey);
        //验证码是否正确都删除，否则验证错误的验证码会存在redis中无法删除
        redisUtil.del(RedisConst.USER_CODE_KEY + verKey);
        if (code == null || StringUtils.isEmpty(code)) {
            throw new AuthenticationServiceException("请输入正确的验证码！");
        }
        //equalsIgnoreCase 比较字符串并且忽略大小写
        if (realCode == null || StringUtils.isEmpty(realCode) || !code.equalsIgnoreCase(realCode)) {
            throw new AuthenticationServiceException("验证码输入错误！！");
        }
        return true;
    }

    //获取用户信息
    @Override
    public UserDetailDTO getUserDetail(String username, HttpServletRequest request, String ipAddress, String ipSource) {
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (Objects.isNull(user)) {
            throw new AuthenticationServiceException(MessageConstant.USER_NOT_EXIST);
        }
        //查询用户角色
        List<String> roleList = roleDao.listRolesByUid(user.getId());
        UserAgent userAgent = IpUtil.getUserAgent(request);
        return UserDetailDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roleList(roleList)
                .loginType(user.getLoginType())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .lastIp(ipAddress)
                .ipSource(ipSource)
                .status(user.isStatus())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")))
                .build();
    }

    @Override
    public User findById(long userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        if (userDao.selectOne(queryWrapper) == null) {
            return null;
        }
        return userDao.selectOne(queryWrapper);
    }

    @Override
    public List<User> getUserList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "username", "nickname", "avatar")
                .orderByAsc("create_time");
        return userDao.selectList(queryWrapper);
    }

    @Override
    public Page<UserBackDTO> getAdminUser(QueryPageVO queryPageVO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, "nickname", queryPageVO.getQueryString());
        Page<UserBackDTO> page = new Page<>();
        page.setTotal(userDao.selectCount(queryWrapper));
        page.setRecords(userDao.getAdminUserPage(queryPageVO));
        return page;
    }

    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        User user = User.builder()
                .id(userDisableVO.getUid())
                .status(userDisableVO.getStatus().equals(1))
                .build();
        userDao.updateById(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getEmail)
                .eq(User::getUsername, resetPasswordDTO.getUsername()));
        if (user == null) {
            throw new BusinessException("该用户不存在，请重新确认");
        }
        if (!user.getEmail().equals(resetPasswordDTO.getEmail())) {
            throw new BusinessException("该账号对应的邮箱不匹配，请重新确认");
        }
        String emailCode = (String) redisUtil.get(RedisConst.EMAIL_CODE_KEY + resetPasswordDTO.getEmail());
        if (!emailCode.equals(resetPasswordDTO.getCode())) {
            throw new BusinessException("验证码输入错误，请重新输入");
        }
        User userVO = new User();
        userVO.setId(user.getId());
        userVO.setStatus(MessageConstant.USER_ABLE);
        userVO.setPassword(encoder.encode(resetPasswordDTO.getPassword()));
        userVO.setUpdateTime(LocalDateTime.now());
        userDao.updateById(userVO);
    }

    /**
     * 用户提供的图片链接无效就自动生成图片
     *
     * @param postUrl 用户传来的头像url
     * @return url
     */
    public String isImagesTrue(String postUrl) {
        if (postUrl.contains("tcefrep.oss-cn-beijing.aliyuncs.com")) { //本人的oss地址，就无需检验图片有效性
            return postUrl;
        }
        int max = 1000;
        int min = 1;
        String picUrl = "https://unsplash.it/100/100?image=";
        try {
            URL url = new URL(postUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return postUrl;
            } else {
                Random random = new Random();
                int s = random.nextInt(max) % (max - min + 1) + min;
                return picUrl + s;
            }
        } catch (Exception e) {   // 代表图片链接无效
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            return picUrl + s;
        }
    }

}
