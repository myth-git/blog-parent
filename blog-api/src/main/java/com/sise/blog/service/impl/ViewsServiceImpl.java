package com.sise.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.ViewsDao;
import com.sise.blog.service.ViewsService;
import com.sise.common.dto.ViewsDTO;
import com.sise.common.pojo.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/1/19 11:43
 */
@Service
public class ViewsServiceImpl extends ServiceImpl<ViewsDao, Views> implements ViewsService {

    @Autowired
    private ViewsDao viewsDao;

    @Override
    public List<ViewsDTO> getViewsData() {
        //前7天时间点
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        //现在时间
        DateTime endTime = DateUtil.endOfDay(new Date());
        return viewsDao.getViewsData(startTime, endTime);
    }
}
