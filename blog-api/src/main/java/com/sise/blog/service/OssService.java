package com.sise.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/11 20:23
 */
public interface OssService {
    /**
     * 用户上传头像
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}
