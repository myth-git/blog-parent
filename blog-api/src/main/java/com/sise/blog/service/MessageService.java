package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.pojo.Message;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/19 11:52
 */
public interface MessageService extends IService<Message> {
    /**
     * 添加留言
     * @param message
     * @param host
     * @return
     */
    boolean addMessage(Message message, String host);

    /**
     * 管理员后台获取留言分页信息
     * @param queryPageVO
     * @return
     */
    Page<Message> getMessagePage(QueryPageVO queryPageVO);

    /**
     * 管理员后台删除留言
     * @param messageIdList
     */
    void deleteMessage(List<Long> messageIdList);

    /**
     * 获取留言列表
     * @return
     */
    List<Message> getMessageList();
}
