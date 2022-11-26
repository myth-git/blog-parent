package com.sise.blog.service.impl;

import com.sise.blog.dao.mapper.TagMapper;
import com.sise.blog.dao.pojo.Tag;
import com.sise.blog.service.TagService;
import com.sise.blog.vo.Result;
import com.sise.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatis plus不支持多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /*
        * 1、标签所拥有的文章的数量最多 最热标签
        * 2、对文章id对应的tag_id有多少进行分组，排序，查找出前limit条
        *
        * */
        List<Long> tagIds = tagMapper.findHostTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        /*
        * 需求是tagId 和 tagName Tag对象
        * select * from tag where id in（1，2，3）
        * */
        List<Tag> tagList = tagMapper.findTagsTagIds(tagIds);
        return Result.success(tagList);
    }


    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

}
