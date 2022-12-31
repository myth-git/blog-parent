package com.sise.common.vo;

import com.sise.common.pojo.Articles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 1:03
 */
@Data
public class ArticlesVO extends Articles implements Serializable {
    private String typeName;    // 分类名称
    private String nickname;    //用户昵称
    private String avatar;      //用户头像

    /**
     * 文章标签
     */
    private List<String> tagNameList;


}
