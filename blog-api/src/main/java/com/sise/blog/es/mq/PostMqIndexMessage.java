package com.sise.blog.es.mq;

import com.sise.common.pojo.Articles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/5 13:17
 */
@Data
@Accessors(chain = true) //可以使用链式访问
@NoArgsConstructor
@AllArgsConstructor
public class PostMqIndexMessage implements Serializable {
    public final static String CREATE_OR_UPDATE = "create_update";
    public final static String REMOVE = "remove";

    private Long blogId;
    private String type;
    private Articles articles;
    private List<Long> blogIdList;

    public PostMqIndexMessage(Long blogId, String type) {
        this.blogId = blogId;
        this.type = type;
    }

    public PostMqIndexMessage(List<Long> blogIdList, String type) {
        this.blogIdList = blogIdList;
        this.type = type;
    }
}
