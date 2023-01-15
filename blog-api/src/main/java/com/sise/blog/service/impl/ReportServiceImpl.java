package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.blog.dao.mapper.ArticlesDao;
import com.sise.blog.dao.mapper.CommentDao;
import com.sise.blog.service.ReportService;
import com.sise.common.pojo.Articles;
import com.sise.common.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
