package com.sise.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.common.pojo.admin.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/6 0:09
 */
@Repository
public interface ResourceDao extends BaseMapper<Resource> {
    /**
     *  获取用户拥有的权限数据
     * @param id
     * @return
     */
    //错误bug最后一行不能有空格   前面的还要加空格
//    @Select("select concat(br.request_method,br.url) result" +
//            "from blog_resource br, blog_user_role ur, blog_role_resource rr" +
//            "where ur.uid = #{id} and ur.rid = rr.rid and br.id = rr.resource_id ")
    @Select("select concat(r.request_method,r.url) result " +
            "from blog_resource r, blog_user_role ur, blog_role_resource rr " +
            "where ur.uid = #{id} and rr.rid = ur.rid and rr.resource_id = r.id")
    List<String> getUserResource(Long id);
}
