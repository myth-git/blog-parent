package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ArticlesDao;
import com.sise.blog.dao.mapper.TypeDao;
import com.sise.blog.service.TypeService;
import com.sise.common.exception.BusinessException;
import com.sise.common.pojo.Articles;
import com.sise.common.pojo.Type;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.TypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 10:27
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

    @Autowired
    private TypeDao typeDao;
    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public List<TypeVO> findType() {
        return typeDao.findType();
    }

    @Override
    public List<Type> getTypeList() {
        List<Type> typeList = typeDao.selectList(null);
        return typeList;
    }

    @Override
    public Page<TypeVO> getAdminType(QueryPageVO queryPageVO) {
        //设置分页条件
        Page<TypeVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, "type_name", queryPageVO.getQueryString());
        page.setTotal(typeDao.selectCount(queryWrapper));
        page.setRecords(typeDao.getAdminType(queryPageVO));
        return page;
    }

    @Override
    public void delete(List<Integer> typeIdList) {
        //查询分类下是否有文章
        Integer count = articlesDao.selectCount(new LambdaQueryWrapper<Articles>()
                .in(Articles::getTypeId, typeIdList));
        if (count > 0) {
            throw new BusinessException("删除失败，分类下有文章");
        }
        typeDao.deleteBatchIds(typeIdList);
    }

    @Override
    public boolean saveOrUpdateType(Type type) {
        Type getType = typeDao.selectOne(new LambdaQueryWrapper<Type>()
                .eq(Type::getTypeName, type.getTypeName())
                .select(Type::getId));
        if (Objects.nonNull(getType)) {
            throw new BusinessException("分类名已存在");
        }
        Type typeAdd = Type.builder()
                .id(type.getId())
                .typeName(type.getTypeName())
                .build();
        this.saveOrUpdate(typeAdd);
        return true;
    }
}
