package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.ad.ContentCategoryDao;
import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.ad.ContentCategoryQuery;
import com.ylt.pojo.entity.PageResult;
import com.ylt.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
//@org.springframework.stereotype.Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryDao contentCategoryDao;
//获取所有
    @Override
    public List<ContentCategory> findAll() {
        return contentCategoryDao.selectByExample(null);
    }

    @Override
    public ContentCategory findOne(Long id) {
        return contentCategoryDao.selectByPrimaryKey(id);
    }

    //获取一条数据
    @Override
    public ContentCategory findById(Long id) {
    return contentCategoryDao.selectByPrimaryKey(id);
}
//分页
    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
        Page<ContentCategory> ContentCategoryList= (Page<ContentCategory>)contentCategoryDao.selectByExample(null);
        return new PageResult(ContentCategoryList.getTotal(),ContentCategoryList.getResult());
    }
//添加
    @Override
    public int save(ContentCategory contentCategory) {
        return contentCategoryDao.insert(contentCategory);
    }
//修改
    @Override
    public int update(ContentCategory contentCategory) {
        return contentCategoryDao.updateByPrimaryKey(contentCategory);
    }

    @Override
    public void delete(Long id) {
        contentCategoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult search(ContentCategory searchContentCategory, Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);

//        获取对应数据
        Page<ContentCategory> ContentCategoryList= (Page<ContentCategory>)contentCategoryDao.selectByExample(null);
        return new PageResult(ContentCategoryList.getTotal(),ContentCategoryList.getResult());
    }


}
