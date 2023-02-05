package com.sise.blog.config;


import com.sise.common.constant.RabbitMQConst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: mq配置类
 * @Author: xzw
 * @Date: 2023/2/5 13:06
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue exQueue() {
        return new Queue(RabbitMQConst.esQueue, true);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(RabbitMQConst.esExchange,true,false);
    }

    @Bean
    public Binding binding(Queue exQueue, DirectExchange exchange){
        return BindingBuilder.bind(exQueue).to(exchange).with(RabbitMQConst.esBingKey);
    }



}
