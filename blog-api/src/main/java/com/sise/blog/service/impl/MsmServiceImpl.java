package com.sise.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.sise.blog.service.MsmService;
import com.sise.blog.utils.RedisUtil;
import com.sise.common.constant.RabbitMQConst;
import com.sise.common.constant.RedisConst;
import com.sise.common.dto.EmailDTO;
import com.sise.common.exception.BusinessException;
import com.sise.common.qq.RandomUtil;
import com.sise.common.utils.CommonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/31 23:26
 */
@Service
public class MsmServiceImpl implements MsmService {
//    @Autowired
//    private JavaMailSender javaMailSender;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;

//    @Async//开启异步，加快获取邮箱速度
    @Override
    public void sendEmail(String email) {
        //校验email
        if (!CommonUtils.checkEmail(email)) {
            throw new BusinessException("请输入正确的邮箱");
        }
        //生成随机的六位验证码
        String code = RandomUtil.getSixBitRandom();
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setSubject("找回密码验证码");
//        simpleMailMessage.setText("尊敬的:" + email + "您的找回密码验证码为：" + code + "有效期10分钟");
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setFrom("1610680426@qq.com");
//        javaMailSender.send(simpleMailMessage);
        EmailDTO emailDTO = EmailDTO.builder()
                                .email(email)
                                .subject("验证码")
                                .content("尊敬的:" + email + "您的验证码为："+ code + "有效时间为10分钟").build();
        amqpTemplate.convertAndSend(RabbitMQConst.EMAIL_EXCHANGE, null, new Message(JSON.toJSONBytes(emailDTO),new MessageProperties()));
        //邮箱验证码存入到redis中，过期时间为10分钟
        redisUtil.set(RedisConst.EMAIL_CODE_KEY + email, code, RedisConst.CODE_EXPIRE_TIME);
    }
}
