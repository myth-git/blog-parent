package com.sise.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.blog.dao.mapper.*;
import com.sise.blog.service.*;
import com.sise.blog.utils.MarkdownUtils;
import com.sise.blog.utils.RedisUtil;
import com.sise.common.constant.CommonConst;
import com.sise.common.constant.RedisConst;
import com.sise.common.dto.*;
import com.sise.common.pojo.*;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.BlogVO;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.TypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2022/12/31 0:37
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesDao,Articles> implements ArticlesService {

    @Autowired
    private ArticlesDao articlesDao;
    @Autowired
    private ArticlesLabelService articlesLabelService;
    @Autowired
    private UserService userService;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ViewsDao viewsDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ViewsService viewsService;
    @Autowired
    private TypeDao typeDao;

    @Override
    public Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO) {
        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryPageVO.getQueryString() != null, "content", queryPageVO.getQueryString());
        page.setTotal(articlesDao.selectCount(queryWrapper));
        page.setRecords(articlesDao.findHomePage(queryPageVO));
        return page;
    }

    @Override
    public List<Articles> latestList() {
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","title","create_time")
                    .last("limit 0,6")
                    .orderByDesc("create_time");
        List<Articles> articles = articlesDao.selectList(queryWrapper);
        return articles;
    }

    @Override
    public Page<ArticlesVO> findPersonBlog(QueryPageVO queryPageVO, Long id) {

        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        //查询文章总数
        page.setTotal(articlesDao.selectCount(queryWrapper));
        page.setRecords(articlesDao.findPersonBlog(queryPageVO, id));
        return page;
    }

    @Override
    public Long addOrUpdate(AddBlogDTO addBlogDTO, Long id) {
        Articles articles = new Articles();
        BeanUtils.copyProperties(addBlogDTO, articles);
        articles.setUserId(id);//设置用户id
        if (addBlogDTO.getId() == null || Objects.isNull(addBlogDTO.getId())){
            //新增博客
            long blogId = IdWorker.getId(Articles.class);
            articles.setId(blogId);//设置文章id
        }else {
            //更新博客,删除文章标签关联表
            articlesLabelService.remove(new LambdaQueryWrapper<ArticlesLabel>().eq(ArticlesLabel::getArticlesId,articles.getId()));
        }
        articles.setFirstPicture(isImagesTrue(articles.getFirstPicture()));
        articles.setUpdateTime(LocalDateTime.now());
        this.saveOrUpdate(articles);
        //插入中间表
        articlesLabelService.addArticlesLabel(articles.getId(),addBlogDTO.getValue());
        return articles.getId();
    }

    @Override
    public BlogVO getBlogDetail(Long id) {
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Articles articles = articlesDao.selectOne(queryWrapper);
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(articles, blogVO);
        User user = userService.getById(articles.getUserId());
        blogVO.setAvatar(user.getAvatar());
        blogVO.setNickname(user.getNickname());
        //获取标签
        blogVO.setTagNameList(labelDao.getLabelNameList(id).stream().map(Label::getLabelName).collect(Collectors.toList()));
        String content = articles.getContent();
        if (content != null) {
            blogVO.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        }
        return blogVO;
    }

    @Override
    public BlogVO getAdminBlogDetail(Long id) {
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Articles articles = articlesDao.selectOne(queryWrapper);
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(articles, blogVO);
        //获取标签
        blogVO.setTagNameList(labelDao.getLabelNameList(id).stream().map(Label::getLabelName).collect(Collectors.toList()));
        String content = articles.getContent();
        if (content != null) {
            blogVO.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        }
        return blogVO;
    }

    //更新博客浏览量,在当前页面连接里，同一篇文章第二次访问就不再添加浏览量
    @Override
    public void updateView(Long id) {
        Set<Long> blogSet = (Set<Long>) Optional.ofNullable(session.getAttribute(CommonConst.BLOG_SET)).orElse(new HashSet<>());
        if (!blogSet.contains(id)) {
            //代表第一次
            blogSet.add(id);
            session.setAttribute(CommonConst.BLOG_SET, blogSet);
            //浏览量加1
            redisUtil.zIncr(RedisConst.BLOG_VIEWS_COUNT, id, 1D);
            setBlogViews(id);
        }


    }

    @Override
    public Page<ArticlesVO> getByTypeId(QueryPageVO queryPageVO) {
        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(queryPageVO.getTypeId() != null, "type_id", queryPageVO.getTypeId());
        page.setTotal(articlesDao.selectCount(queryWrapper));
        page.setRecords(articlesDao.findHomePage(queryPageVO));
        return page;
    }

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Integer viewsCount = viewsDao.selectOne(new QueryWrapper<Views>().select("SUM(count) as count")).getCount();
        // 查询留言量
        Integer messageCount = messageDao.selectCount(null);
        // 查询用户量
        Integer userCount = userDao.selectCount(null);
        // 查询文章量
        Integer blogCount = articlesDao.selectCount(null);
        // 查询一周访问量
        List<ViewsDTO> viewsDTOList = viewsService.getViewsData();
        // 查询文章统计
        List<BlogStatisticsDTO> blogStatisticsDTOList = articlesDao.listArticleStatistics();
        // 查询分类数据
        List<TypeVO> typeVOList = typeDao.findType();
        // 查询标签数据
        List<Label> labelList = labelDao.selectList(null);
        // 查询博客浏览量前五
        List<Articles> articles = articlesDao.selectList(new LambdaQueryWrapper<Articles>()
                .select(Articles::getId, Articles::getTitle, Articles::getViews)
                .last("limit 5").orderByDesc(Articles::getViews));
        List<BlogRankDTO> blogRankDTOList = articles.stream().map(t ->
                BlogRankDTO.builder()
                        .title(t.getTitle())
                        .views(t.getViews())
                        .build()
        ).sorted(Comparator.comparingInt(BlogRankDTO::getViews).reversed()).collect(Collectors.toList());
        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .articleStatisticsList(blogStatisticsDTOList)
                .tagList(labelList)
                .typeList(typeVOList)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(blogCount)
                .blogRankDTOList(blogRankDTOList)
                .viewsDTOList(viewsDTOList)
                .build();
        return blogBackInfoDTO;
    }

    private void setBlogViews(Long id) {
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("views")
                    .eq("id", id);
        Articles articles = articlesDao.selectOne(queryWrapper);
        this.update(new UpdateWrapper<Articles>()
                        .set("views", articles.getViews()+1)
                        .eq("id", id));
    }

    /**
     * 用户提供的图片链接无效就自动生成图片
     *
     * @param postUrl 首图url
     * @return 图片url
     */
    public String isImagesTrue(String postUrl) {
        if (postUrl.contains("tcefrep.oss-cn-beijing.aliyuncs.com")) {   //本人的oss地址，就无需检验图片有效性
            return postUrl;
        }
        int max = 1000;
        int min = 1;
        String picUrl = "https://unsplash.it/800/450?image=";
        try {
            URL url = new URL(postUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            if (urlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return postUrl;
            } else {
                Random random = new Random();
                int s = random.nextInt(max) % (max - min + 1) + min;
                return picUrl + s;
            }
        } catch (Exception e) {   // 代表图片链接无效
            Random random = new Random();
            int s = random.nextInt(max) % (max - min + 1) + min;
            return picUrl + s;
        }
    }

}

