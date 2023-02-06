package com.sise.blog.email;

import com.alibaba.fastjson.JSON;
import com.sise.blog.utils.RedisUtil;
import com.sise.common.constant.RabbitMQConst;
import com.sise.common.constant.RedisConst;
import com.sise.common.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Description: 邮箱消费者
 * @Author: xzw
 * @Date: 2023/2/6 23:54
 */
@Component
@RabbitListener(queues = RabbitMQConst.EMAIL_QUEUE)
public class EmailConsumer {

    @Value("${spring.mail.username}")
    private String email;
    @Autowired
    private JavaMailSender javaMailSender;


    @RabbitHandler
    public void process(byte[] data) {
        EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);
        simpleMailMessage.setTo(emailDTO.getEmail());
        simpleMailMessage.setSubject(emailDTO.getSubject());
        simpleMailMessage.setText(emailDTO.getContent());
        javaMailSender.send(simpleMailMessage);
    }
}
