package com.sise.common.dto;

import com.sise.common.pojo.Label;
import com.sise.common.vo.TypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 管理员后台信息dto
 * @Author: xzw
 * @Date: 2023/1/19 11:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogBackInfoDTO {
    /**
     * 访问量
     */
    private Integer viewsCount;

    /**
     * 留言量
     */
    private Integer messageCount;

    /**
     * 用户量
     */
    private Integer userCount;

    /**
     * 文章量
     */
    private Integer articleCount;

    /**
     * 分类统计
     */
    private List<TypeVO> typeList;

    /**
     * 标签列表
     */
    private List<Label> tagList;

    /**
     * 文章统计列表
     */
    private List<BlogStatisticsDTO> articleStatisticsList;

    /**
     * 一周用户量集合
     */
    private List<ViewsDTO> viewsDTOList;

    /**
     * 文章浏览量排行
     */
    private List<BlogRankDTO> blogRankDTOList;
}
