package com.sise.blog.es.mq;

import com.sise.blog.es.index.BlogInfo;
import com.sise.blog.es.repository.BlogInfoMapper;
import com.sise.blog.service.BlogInfoService;
import com.sise.common.constant.RabbitMQConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: mq监听es队列消息
 * @Author: xzw
 * @Date: 2023/2/5 13:39
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConst.esQueue)
public class MqMessageHandler {

    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    @RabbitHandler
    public void handler(PostMqIndexMessage message) {
        switch (message.getType()) {
            case PostMqIndexMessage.CREATE_OR_UPDATE:
                blogInfoService.createOrUpdate(message);
                log.info("es添加或更新博客成功，id为：{}", message.getBlogId());
                break;
            case PostMqIndexMessage.REMOVE:
                List<BlogInfo> blogInfoList = message.getBlogIdList().stream()
                        .map(blogId -> BlogInfo.builder().id(blogId).build()) //把message实体blogId全部放到blogInfo实体里面
                        .collect(Collectors.toList());
                blogInfoMapper.deleteAll(blogInfoList);//删除es文档对应的索引
                log.info("es删除博客成功，id为：{}", message.getBlogIdList());
                break;
            default:
                log.error("没有找到对应的消息类型，请注意：{}", message.toString());
        }

    }
}
