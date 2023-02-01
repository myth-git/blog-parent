package com.sise.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 公共工具列
 * @Author: xzw
 * @Date: 2023/2/1 22:09
 */
public class CommonUtils {

    /**
     * 检测邮箱是否合法
     *
     * @param email 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String email) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }
}
