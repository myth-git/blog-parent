package com.sise.blog.controller.login;

import com.sise.common.constant.MessageConstant;
import com.sise.blog.utils.RedisUtil;
import com.sise.blog.vo.Result;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

import static com.sise.common.constant.RedisConst.*;

@RestController
@Api(value = "用户信息管理模块", description = "管理用户信息验证")
@RequestMapping("admapi")
public class LoginAdminController {

    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "生成验证码")
    @GetMapping("/captcha")
    public Result captcha(HttpServletRequest request, HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        //生成验证码
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        //存入redis中并设置10分钟
        redisUtil.set(USER_CODE_KEY + key, verCode, 600);
        //存入到session中
        request.getSession().setAttribute("CAPTCHA", verCode);
        HashMap<String, Object> code = new HashMap<>();
        code.put("verKey", key);
        code.put("verCode", specCaptcha.toBase64());
        //将key和base64返回给前端
        return Result.ok(MessageConstant.VERIFICATION_CODE_SUCCESS, code);

    }
}
