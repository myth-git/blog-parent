package com.sise.gateway.api;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: 需要用feign调用blog-api的接口
 * @Author: xzw
 * @Date: 2023/1/5 22:13
 */
@FeignClient(value = "BLOG-SERVER")
public interface ResourceApi {

    //获取需要鉴权的资源
    @GetMapping("/resource/list")
    public List<String> getResourceList();

    @GetMapping("/resource/getUserResource")
    public List<String> getUserResource(@RequestParam("id") String id);
}
