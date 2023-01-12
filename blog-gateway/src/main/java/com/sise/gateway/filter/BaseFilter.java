package com.sise.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sise.common.entity.Result;
import com.sise.gateway.config.IgnoreTokenConfig;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 基础过滤器
 * @Author: xzw
 * @Date: 2023/1/5 21:27
 */
@Slf4j
public abstract class BaseFilter extends ZuulFilter {
    //判断当前请求uri是否需要放行
    protected boolean isIgnoreToken() {
        //动态获取当前请求的uri
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String uri = request.getRequestURI();
        // /api/server/.... 截取第三个/以及之后的字符串
        String result = uri.substring(uri.indexOf("/server") + 7);
        log.info("是否放行不鉴权" + result + "请求：{}", IgnoreTokenConfig.isIgnoreToken(result));
        return IgnoreTokenConfig.isIgnoreToken(result);
    }

    //用户没有权限，网关抛出异常
    protected void errorResponse(String errMsg, int errCode, int httpStatusCode) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setResponseStatusCode(httpStatusCode);
        currentContext.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
        if (currentContext.getResponseBody() == null) {
            currentContext.setResponseBody(JSON.toJSONString(new Result(false, errCode, errMsg, errMsg)));
            //不进行路由，直接返回
            currentContext.setSendZuulResponse(false);
        }
    }
}
