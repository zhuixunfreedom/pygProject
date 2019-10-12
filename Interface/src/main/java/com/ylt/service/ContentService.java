package com.ylt.service;

import com.ylt.pojo.ad.Content;
import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.entity.PageResult;

import java.util.List;

public interface ContentService {
    void delete(Long id);

    int update(Content content);

    int save(Content content);

    PageResult search(Content searchContent, Integer page, Integer rows);

    PageResult findPage(Integer page, Integer rows);

    Content findById(Long id);

    List<Content> findAll();

    Content findOne(Long id);

    List<Content>  findByCategoryId(Long categoryId);

    List<Content> findByCategoryIdFromRedis(Long categoryId);
}
