package com.sise.blog.service;


import com.sise.blog.es.mq.PostMqIndexMessage;
import com.sise.common.vo.PageLightVO;
import com.sise.common.vo.QueryPageVO;

import java.io.IOException;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/4 20:38
 */
public interface BlogInfoService {
    /**
     *  分页高亮条件查询
     * @param queryPageVO
     * @return
     */
    PageLightVO highLightSearchPage(QueryPageVO queryPageVO) throws IOException;

    /**
     * 新增或更新博客时更新es
     * @param message
     */
    void createOrUpdate(PostMqIndexMessage message);
}
