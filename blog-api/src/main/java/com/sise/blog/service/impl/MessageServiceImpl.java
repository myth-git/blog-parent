package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.MessageDao;
import com.sise.blog.service.MessageService;
import com.sise.blog.utils.IpUtil;
import com.sise.blog.utils.IpUtils;
import com.sise.common.pojo.Message;
import com.sise.common.vo.QueryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/19 11:53
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public boolean addMessage(Message message, String host) {
        message.setIp("host");
        String ipSource = IpUtil.getIpSource(host);
        message.setIpSource(ipSource);
        message.setMessageContent(message.getMessageContent());
        int i = messageDao.insert(message);
        return i == 1;
    }

    @Override
    public Page<Message> getMessagePage(QueryPageVO queryPageVO) {
        Page<Message> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, Message::getNickname, queryPageVO.getQueryString());
        return messageDao.selectPage(page, queryWrapper);
    }

    @Override
    public void deleteMessage(List<Long> messageIdList) {
        messageDao.deleteBatchIds(messageIdList);
    }

    @Override
    public List<Message> getMessageList() {
        return messageDao.selectList(null);
    }
}
