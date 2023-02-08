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
import com.sise.common.utils.TimeUtils;
import com.sise.common.vo.ArticlesVO;
import com.sise.common.vo.BlogVO;
import com.sise.common.vo.QueryPageVO;
import com.sise.common.vo.TypeVO;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @Autowired
    private ArticlesLabelDao articlesLabelDao;
    @Autowired
    private ThumbsUpDao thumbsUpDao;
    @Autowired
    private FavoritesDao favoritesDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<ArticlesVO> findHomePage(QueryPageVO queryPageVO) {
        Page<ArticlesVO> page = new Page<>(queryPageVO.getCurrentPage(), queryPageVO.getPageSize());
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published",0).like(queryPageVO.getQueryString() != null, "content", queryPageVO.getQueryString());
        page.setTotal(articlesDao.selectCount(queryWrapper));
        page.setRecords(articlesDao.findHomePage(queryPageVO));
        return page;
    }

    @Override
    public List<Articles> latestList() {
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","title","create_time")
                    .eq("published",0)
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
    @Transactional(rollbackFor = Exception.class)//要么全部执行成功，要么全部执行失败
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

    @Transactional(rollbackFor = Exception.class)//要么全部执行成功，要么全部执行失败
    @Override
    public Long addDraft(AddBlogDTO addBlogDTO, Long id) {
        Articles articles = new Articles();
        BeanUtils.copyProperties(addBlogDTO, articles);
        articles.setUserId(id);
        articles.setPublished(true);//表示保存为草稿状态
        articles.setId(IdWorker.getId());//获取文章id
        articles.setFirstPicture(isImagesTrue(articles.getFirstPicture()));
        articles.setUpdateTime(LocalDateTime.now());
        this.save(articles);
        articlesLabelService.addArticlesLabel(articles.getId(), addBlogDTO.getValue());//保存文章和标签关联表
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
        //增加文章浏览量
        List<Views> viewsList = viewsDao.selectList(
                new LambdaQueryWrapper<Views>()
                .eq(Views::getBlogId, id)
                .orderByDesc(Views::getCreateTime));//倒序获取集合，目的方便后面添加数据
        System.out.println("集合：" + viewsList);
        if (!viewsList.isEmpty()) {
            for (Views views : viewsList) {
                boolean sameDate = TimeUtils.isSameDate(views.getCreateTime(), LocalDateTime.now());
                if (sameDate) {
                    views.setCount(views.getCount() + 1);
                    viewsDao.updateById(views);
                } else {
                    Views views1 = new Views();
                    views1.setCreateTime(LocalDateTime.now());
                    views1.setBlogId(id);
                    views1.setCount(0);
                    views1.setCount(views1.getCount() + 1);
                    viewsDao.insert(views1);
                }
                break; //跳出循环
            }
        } else {
            Views views = new Views();
            views.setBlogId(id);
            views.setCreateTime(LocalDateTime.now());
            views.setCount(0);
            views.setCount(views.getCount() + 1);
            viewsDao.insert(views);
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
        //获取标签id,不能直接获取名称，否则更新时标签类型会不一致
        blogVO.setTagNameIds(labelDao.getLabelNameList(id).stream().map(Label::getId).collect(Collectors.toList()));
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
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 0);//排除草稿文章
        Integer blogCount = articlesDao.selectCount(queryWrapper);
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

    @Override
    public Page<BlogAdminDTO> adminBlogPage(QueryPageVO queryPageVO) {
        Page<BlogAdminDTO> page = new Page<>();
        page.setRecords(articlesDao.BlogAdminPage(queryPageVO));
        page.setTotal(articlesDao.BlogAdminPageCount(queryPageVO));
        return page;
    }

    @Override
    public void deleteBlogs(List<Long> blogIdList) {
        //删除博客标签的中间表数据
        articlesLabelDao.delete(new LambdaQueryWrapper<ArticlesLabel>()
                        .in(ArticlesLabel::getArticlesId, blogIdList));
        //删除博客的点赞、收藏信息和评论信息
        thumbsUpDao.delete(new LambdaQueryWrapper<ThumbsUp>()
                    .in(ThumbsUp::getBlogId, blogIdList));
        favoritesDao.delete(new LambdaQueryWrapper<Favorites>()
                    .in(Favorites::getBlogId, blogIdList));
        commentDao.delete(new LambdaQueryWrapper<Comment>()
                  .in(Comment::getBlogId, blogIdList));
        //删除博客
        articlesDao.deleteBatchIds(blogIdList);
    }

    @Override
    public boolean thumbsUp(Long blogId, Long uid) {
        LambdaQueryWrapper<ThumbsUp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ThumbsUp::getBlogId, blogId)
                    .eq(ThumbsUp::getUid, uid);
        Articles articles = articlesDao.selectById(blogId);
        if (thumbsUpDao.selectCount(queryWrapper) > 0){//说明该文章已点过赞
            //删除点赞表记录
            thumbsUpDao.delete(queryWrapper);
            //该文章点赞数-1
            articles.setThumbs(articles.getThumbs() - 1);
            articlesDao.updateById(articles);
            return false;
        }
        ThumbsUp thumbsUp = new ThumbsUp();
        thumbsUp.setBlogId(blogId);
        thumbsUp.setUid(uid);
        thumbsUpDao.insert(thumbsUp);
        articles.setThumbs(articles.getThumbs() + 1);
        articlesDao.updateById(articles);
        return true;
    }

    @Override
    public boolean favorite(Long blogId, Long uid) {
        LambdaQueryWrapper<Favorites> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorites::getBlogId, blogId)
                    .eq(Favorites::getUid, uid);
        Integer selectCount = favoritesDao.selectCount(queryWrapper);
        if (selectCount > 0){
            favoritesDao.delete(queryWrapper);
            return false;
        }
        Favorites favorites = new Favorites();
        favorites.setBlogId(blogId);
        favorites.setUid(uid);
        favoritesDao.insert(favorites);
        return true;
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

