package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.blog.dao.mapper.ArticlesDao;
import com.sise.blog.dao.mapper.CommentDao;
import com.sise.blog.dao.mapper.FavoritesDao;
import com.sise.blog.dao.mapper.ThumbsUpDao;
import com.sise.blog.service.ReportService;
import com.sise.common.pojo.*;
import com.sise.common.vo.BlogDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/15 22:38
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ArticlesDao articlesDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private FavoritesDao favoritesDao;
    @Autowired
    private ThumbsUpDao thumbsUpDao;

    private Integer j = 0;
    private Map<String, Object> map;  // 相当于option
    private ArrayList<Object> series;   // 相当于series
    private HashMap<Object, Object> seriesMap; // 里面有个data数组存放要渲染的数据

    @Override
    public Map<String, Object> getReport2(Long id) throws Exception {
        map = new HashMap<>();  //相当于option
        series = new ArrayList<>(); //相当于series
        Map<String, Object> legend = new HashMap<>();  //相当于legend
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id).last("limit 4"); //查出4条
        Integer count = articlesDao.selectCount(queryWrapper);
        queryWrapper.select("title", "views", "thumbs", "id").orderByDesc("views");
        List<Articles> articlesList = articlesDao.selectList(queryWrapper);

        String[] legendData = getLegendData(count, articlesList);
        legend.put("data", legendData);

        for (Articles articles : articlesList) {
            seriesMap = new HashMap<>();
            seriesMap.put("name", articles.getTitle());
            seriesMap.put("type", "bar");
            seriesMap.put("data", getSeriesData2(articles));
            series.add(seriesMap);
        }
        map.put("series", series);
        map.put("legend", legend);
        return map;
    }

    @Override
    public List<BlogDataVO> getReport1(Long id) {
        //查询该用户写的总文章量
        LambdaQueryWrapper<Articles> queryWrapperArticles = new LambdaQueryWrapper<>();
        queryWrapperArticles.eq(Articles::isPublished,false)//排除草稿状态
                    .eq(Articles::getUserId, id);
        Integer articlesCount = articlesDao.selectCount(queryWrapperArticles);

        //查询该用户写的文章量的总评论量
        List<Long> blogIdList = articlesDao.selectList(queryWrapperArticles)
                                         .stream()
                                         .map(Articles::getId)
                                         .collect(Collectors.toList());//查出该用户写的文章id
        LambdaQueryWrapper<Comment> queryWrapperComment = new LambdaQueryWrapper<>();
        queryWrapperComment.in(Comment::getBlogId,blogIdList);
        Integer commentCount = commentDao.selectCount(queryWrapperComment);

        //查询该用户写的文章量的总收藏量
        LambdaQueryWrapper<Favorites> queryWrapperFavorites = new LambdaQueryWrapper<>();
        queryWrapperFavorites.in(Favorites::getBlogId,blogIdList);
        Integer favoritesCount = favoritesDao.selectCount(queryWrapperFavorites);

        //查询该用户写的文章量的总点赞量
        LambdaQueryWrapper<ThumbsUp> queryWrapperThumbsUp = new LambdaQueryWrapper<>();
        queryWrapperThumbsUp.in(ThumbsUp::getBlogId, blogIdList);
        Integer thumbsUpCount = thumbsUpDao.selectCount(queryWrapperThumbsUp);


        //查询该用户写的文章量的总浏览量
        List<Integer> blogViews = articlesDao.selectList(queryWrapperArticles)
                .stream()
                .map(Articles::getViews)
                .collect(Collectors.toList());

        Integer viewsCount = 0;
        for (int i = 0; i < blogViews.size(); i++) {
            Integer integer = blogViews.get(i);
            viewsCount = viewsCount + integer;
        }
        BlogDataVO blogDataVO = new BlogDataVO();
        blogDataVO.setLabelName("文章数");
        blogDataVO.setCount(articlesCount);
        BlogDataVO blogDataVO1 = new BlogDataVO();
        blogDataVO1.setLabelName("评论数");
        blogDataVO1.setCount(commentCount);
        BlogDataVO blogDataVO2 = new BlogDataVO();
        blogDataVO2.setLabelName("收藏量");
        blogDataVO2.setCount(favoritesCount);
        BlogDataVO blogDataVO3 = new BlogDataVO();
        blogDataVO3.setLabelName("浏览量");
        blogDataVO3.setCount(viewsCount);
        BlogDataVO blogDataVO4 = new BlogDataVO();
        blogDataVO4.setLabelName("点赞数");
        blogDataVO4.setCount(thumbsUpCount);
        List<BlogDataVO> list = new ArrayList<>();
        list.add(blogDataVO);
        list.add(blogDataVO2);
        list.add(blogDataVO1);
        list.add(blogDataVO3);
        list.add(blogDataVO4);
        return list;
    }

    private Integer[] getSeriesData2(Articles articles) {
        Integer[] data = new Integer[3];
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", articles.getId());
        Integer count = commentDao.selectCount(queryWrapper);
        data[0] = articles.getThumbs();
        data[1] = count; //评论数
        data[2] = articles.getViews(); //浏览数
        return data;
    }

    private String[] getLegendData(Integer count, List<Articles> articlesList) {
        j = 0;
        String[] data = new String[count];
        for (Articles articles : articlesList) {
            data[j++] = articles.getTitle();
        }
        return data;
    }
}
