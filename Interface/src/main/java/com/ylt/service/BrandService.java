package com.ylt.service;

import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.good.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
//    获取所有
    List<Brand> findAll();

    //获取一条数据
    Brand findById(Long id);

    //  分页
    PageResult findPage(Integer page, Integer rows);
//  添加
    int save(Brand brand);
//  修改
    int update(Brand brand);

    void delete(Long id);

    PageResult search(Brand searchBrand, Integer page, Integer rows);

    List<Map> selectOptionList();
}
