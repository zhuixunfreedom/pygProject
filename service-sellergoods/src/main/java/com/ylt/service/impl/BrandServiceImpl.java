package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.good.BrandDao;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.good.Brand;
import com.ylt.pojo.good.BrandQuery;
import com.ylt.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
//@org.springframework.stereotype.Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;
//获取所有
    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }
//获取一条数据
    @Override
    public Brand findById(Long id) {
    return brandDao.selectByPrimaryKey(id);
}
//分页
    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
        Page<Brand> brandList= (Page<Brand>)brandDao.selectByExample(null);
        return new PageResult(brandList.getTotal(),brandList.getResult());
    }
//添加
    @Override
    public int save(Brand brand) {
        return brandDao.insert(brand);
    }
//修改
    @Override
    public int update(Brand brand) {
        return brandDao.updateByPrimaryKey(brand);
    }

    @Override
    public void delete(Long id) {
        brandDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult search(Brand searchBrand, Integer page, Integer rows) {
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
        // 条件查询对象
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        //添加条件
        if(searchBrand.getName()!=null){
            criteria.andNameLike("%"+searchBrand.getName()+"%");
        }
        if(searchBrand.getFirstChar()!=null){
            criteria.andFirstCharEqualTo(searchBrand.getFirstChar());
        }
//        获取对应数据
        Page<Brand> brandList= (Page<Brand>)brandDao.selectByExample(brandQuery);
        return new PageResult(brandList.getTotal(),brandList.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }
}
