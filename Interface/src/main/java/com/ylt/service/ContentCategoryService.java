package com.ylt.service;

import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.entity.PageResult;

import java.util.List;

public interface ContentCategoryService {
    void delete(Long id);

    int update(ContentCategory contentCategory);

    int save(ContentCategory contentCategory);

    PageResult search(ContentCategory searchContentCategory, Integer page, Integer rows);

    PageResult findPage(Integer page, Integer rows);

    ContentCategory findById(Long id);

    List<ContentCategory> findAll();

    ContentCategory findOne(Long id);
}
