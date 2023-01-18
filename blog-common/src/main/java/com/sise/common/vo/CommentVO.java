package com.sise.common.vo;

import com.sise.common.pojo.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 评论VO
 * @Author: xzw
 * @Date: 2023/1/18 14:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends Comment implements Serializable {

    private String nickname;    //自己的昵称

    private String avatar;

    private String title;

    private List<CommentVO> children;

    private String replyNickname;   //回复的人的昵称
}
