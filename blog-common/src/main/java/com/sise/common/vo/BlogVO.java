package com.sise.common.vo;

import com.sise.common.pojo.Articles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 返货博客信息VO
 * @Author: xzw
 * @Date: 2023/1/14 0:15
 */
@Data
public class BlogVO extends Articles implements Serializable {
    private String typeName;    // 分类名称
    private String nickname;    //用户昵称
    private String avatar;      //用户头像

    /**
     * 文章标签
     */
    private List<String> tagNameList;
}
