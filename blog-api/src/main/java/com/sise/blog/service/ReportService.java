package com.sise.blog.service;

import com.sise.common.vo.BlogDataVO;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 22:38
 */
public interface ReportService {
    /**
     * 获取单篇博文数据
     * @param id
     * @return
     */
    Map<String, Object> getReport2(Long id) throws Exception;

    /**
     * 获取用户后台博文数据
     * @param id
     * @return
     */
    List<BlogDataVO> getReport1(Long id);
}
