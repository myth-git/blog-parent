package com.sise.blog.service.impl;

import com.sise.blog.service.BlogInfoService;
import com.sise.common.vo.PageLightVO;
import com.sise.common.vo.QueryPageVO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/4 20:38
 */
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public PageLightVO highLightSearchPage(QueryPageVO queryPageVO) throws IOException {

        //1、构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from((queryPageVO.getCurrentPage() - 1) * queryPageVO.getPageSize());
        searchSourceBuilder.size(queryPageVO.getPageSize());

        //匹配查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryPageVO.getQueryString(), "title", "content");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        //60就要响应相关内容不管查询多少
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("content");
        highlightBuilder.requireFieldMatch(false);//有多个高亮是显示第一个即可
        highlightBuilder.preTags("<span style = 'color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        //2、执行查询
        SearchRequest searchRequest = new SearchRequest("blog-search");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //3、解析结果, 将原来没有高亮字段替换到高亮字段
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            HighlightField content = highlightFields.get("content");
            //原来的字段
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            sourceAsMap = replaceHits(title, sourceAsMap, "title");
            sourceAsMap = replaceHits(content, sourceAsMap, "content");
            sourceAsMap.put("blogId", hit.getId());
            list.add(sourceAsMap);
        }
        PageLightVO pageLightVO = new PageLightVO();
        pageLightVO.setTotal(total(queryPageVO.getQueryString(), true));
        pageLightVO.setRecords(list);
        return pageLightVO;

    }

    /**
     * 原字段替换高亮字段
     * @param field
     * @param sourceAsMap
     * @param type
     * @return
     */
    public Map<String, Object> replaceHits(HighlightField field, Map<String, Object> sourceAsMap, String type) {
        if (field != null) {   // 解析高亮字段，将原来字段替换为高亮字段
            Text[] fragments = field.fragments();
            String n_field = "";
            for (Text text : fragments) {
                n_field += text;
                break;
            }
            sourceAsMap.put(type, n_field); //高亮字段替换掉原来内容
        }
        return sourceAsMap;
    }

    public int total(String queryString, boolean flag) throws IOException {
        //构建查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建匹配查询
        if (flag) {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(queryString, "title", "content");
            searchSourceBuilder.query(multiMatchQueryBuilder);
        }
        SearchRequest searchRequest = new SearchRequest("blog-search");
        //执行查询
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return (int) searchResponse.getHits().getTotalHits().value;
    }
}
