package com.sise.blog.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: ElasticSearchConfig配置类
 * @Author: xzw
 * @Date: 2023/2/4 20:15
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${myth.es.hostlist}")
    private String hostList;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        //取出es节点，逗号分隔
        String[] split = hostList.split(",");
        HttpHost[] httpHostsArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostsArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHostsArray));
        return client;
    }

//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost",9200,"http")
//                )
//        );
//        return client;
//    }
}
