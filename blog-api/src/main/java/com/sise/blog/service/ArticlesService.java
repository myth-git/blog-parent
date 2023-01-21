package com.sise.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.common.dto.AddBlogDTO;
import com.sise.common.dto.BlogBackInfoDTO;
import com.sise.common.pojo.Articles;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.BlogVO;
import com.sise.common.vo.QueryPageVO;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 0:37
 */
public interface ArticlesService extends IService<Articles> {
    /**
     * 渲染首页的分页数据(按阅读量降序)
     * @param queryPageVO
     * @return Page<BlogVO>
     */
    Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO);
    /**
     * 按照时间降序获取最新推荐的博客列表
     * @return list
     */
    List<Articles> latestList();

    /**
     * 获取个人后台博客分页列表
     * @param queryPageVO
     * @param id
     * @return
     */
    Page<ArticlesVO> findPersonBlog(QueryPageVO queryPageVO, Long id);

    /**
     * 用户添加或更新博客
     * @param addBlogDTO
     * @param id
     * @return
     */
    Long addOrUpdate(AddBlogDTO addBlogDTO, Long id);

    /**
     * 根据id获取博客的信息
     * @param id
     * @return
     */
    BlogVO getBlogDetail(Long id);

    /**
     * 后台编辑获取根据id博客信息
     * @param id
     * @return
     */
    BlogVO getAdminBlogDetail(Long id);

    /**
     * 更新博客浏览量
     * @param id
     */
    void updateView(Long id);

    /**
     * 根据分类id获取博客分页数据
     * @param queryPageVO
     * @return
     */
    Page<ArticlesVO> getByTypeId(QueryPageVO queryPageVO);

    /**
     * 查看管理员后台信息
     * @return
     */
    BlogBackInfoDTO getBlogBackInfo();
}
