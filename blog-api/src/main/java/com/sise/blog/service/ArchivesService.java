package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.common.pojo.Articles;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/24 22:18
 */
public interface ArchivesService {

    Page<Articles> getArchivesList();
}
