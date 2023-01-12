package com.sise.gateway.filter;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.sise.gateway.rsa.RsaKeys;
import com.sise.gateway.service.RsaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * @Description: 解密网关过滤器
 * @Author: xzw
 * @Date: 2023/1/5 21:48
 */
@Component
@Slf4j
@CrossOrigin
public class RSARequestFilter extends BaseFilter {

    @Resource
    private RsaService rsaService;

    private String tokenHeader = "Authorization";

    @Override
    public String filterType() {
        //过滤器在什么环境下执行，解密操作需要在转发之前执行
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置过滤器的执行顺序，数值越大顺序越后
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        //是否使用过滤器
        return true;
    }

    /**
     * 处理加密过的参数，进行解密
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        /*
         * 1. 从request body中读取出加密后的请求参数
         * 2. 将加密后的请求参数用私钥解密
         * 3. 将解密后的请求参数写回request body中
         * 4. 转发请求
         */
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getHeader(this.tokenHeader);
        //声明存放加密后的数据变量
        String requestData = null;
        //声明解密后的数据变量
        String decryptData = null;
        //网关从前端接收过来的request后，还要再加上token转发request，否则后端服务器会拦截
        currentContext.addZuulResponseHeader("Authorization", token);
        //需要设置request请求头中的Content-Type为json格式，否则api接口模块就需要进行url转码操作
        currentContext.addZuulRequestHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON) + ";charset=UTF-8");
        //解密参数
        try {
            //通过request获取inputStream
            ServletInputStream inputStream = request.getInputStream();

            //从inputStream中得到加密后的数据
            requestData = StreamUtils.copyToString(inputStream, Charsets.UTF_8);
            String s = URLDecoder.decode(requestData, "UTF-8");
            String s2 = s.replace(' ', '+');
            if (requestData != null && s2 != null) {
            }
            //对加密后的数据进行解密
            if (!Strings.isNullOrEmpty(s2)) {
                try {
                    //运用私钥进行解密
                    decryptData = rsaService.RSADecryptDataPEM(s2, RsaKeys.getServerPrvKeyPkcs8());
                } catch (BadPaddingException e) {
//                    e.printStackTrace();
                    System.out.println("网关发送的是明文数据");
                }
                System.out.println("解密后" + decryptData);
            }

            if (!Strings.isNullOrEmpty(decryptData)) {
                byte[] bytes = decryptData.getBytes();

                //使用RequestContext进行数据的转发
                currentContext.setRequest(new HttpServletRequestWrapper(request) {

                    @Override
                    public String getHeader(String name) {
                        return token;
                    }

                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(bytes);
                    }

                    @Override
                    public int getContentLength() {
                        return bytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return bytes.length;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
