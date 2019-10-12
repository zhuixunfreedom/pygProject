package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.ad.ContentCategoryDao;
import com.ylt.dao.ad.ContentDao;
import com.ylt.pojo.ad.Content;
import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.ad.ContentCategoryQuery;
import com.ylt.pojo.ad.ContentQuery;
import com.ylt.pojo.entity.PageResult;
import com.ylt.util.Constants;
import com.ylt.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
//@org.springframework.stereotype.Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ContentDao contentDao;
//获取所有
    @Override
    public List<Content> findAll() {
        return contentDao.selectByExample(null);
    }

    @Override
    public Content findOne(Long id) {
        return contentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        ContentQuery contentQuery = new ContentQuery();
        ContentQuery.Criteria criteria = contentQuery.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        return contentDao.selectByExample(contentQuery);
    }

    //获取一条数据
    @Override
    public Content findById(Long id) {
    return contentDao.selectByPrimaryKey(id);
}
//分页
    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
        Page<Content> ContentList= (Page<Content>)contentDao.selectByExample(null);
        return new PageResult(ContentList.getTotal(),ContentList.getResult());
    }
    // 添加
    @Override
    public int save(Content content) {
        //1 将新的广添加到数据库中
        int i = contentDao.insertSelective(content);
        //2 根据分类id  到redis中删除对应的分类广告集合
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());

        return i;
    }

    @Override
    public int update(Content content) {
        // 1 根据广告id  到数据库中查询原来广告对象
        Content oldContent = contentDao.selectByPrimaryKey(content.getId());
        //2 根据原来广告对象 id  到redis 中删除对应的广告集合
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(oldContent.getCategoryId());
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());
        return contentDao.updateByPrimaryKeySelective(content);
    }

    @Override
    public void delete(Long id) {
        // 1 根据广告的id 到mysql 数据库中查询广告对象
        Content content = contentDao.selectByPrimaryKey(id);
        // 2  根据广告对象中的分类id   删除redis中对应的广告集合数据
        redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).delete(content.getCategoryId());
        //3 根据广告id  删除mysql 数据库中的广告数据
        contentDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult search(Content searchContent, Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
//        获取对应数据
        Page<Content> ContentList= (Page<Content>)contentDao.selectByExample(null);
        return new PageResult(ContentList.getTotal(),ContentList.getResult());
    }


    // 从redis中取数据
    @Override
    public List<Content> findByCategoryIdFromRedis(Long categoryId) {
        //1 根据分类id 到redis  数据库取数据
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).get(categoryId);
        // 2  如果redis中没有数据  则道mysql取数据
        if(contentList==null){
            // 3 如果取到数据  存一份到redis中
            contentList = findByCategoryId(categoryId);
            redisTemplate.boundHashOps(Constants.CONTENT_LIST_REDIS).put(categoryId,contentList);
        }

        return contentList;
    }


}
