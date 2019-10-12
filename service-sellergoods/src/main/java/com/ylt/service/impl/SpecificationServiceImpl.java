package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.specification.SpecificationDao;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.specification.Specification;
import com.ylt.pojo.specification.SpecificationOption;
import com.ylt.pojo.specification.SpecificationQuery;
import com.ylt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SpecificationServiceImpl implements SpecificationService{
    @Autowired
    private SpecificationDao specificationDao;
    @Override
    public PageResult search(String name, Integer page, Integer rows) {

        //查询对象
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if(name!=null){
            criteria.andSpecNameLike("%"+name+"%");
        }
        //  分页助手  第一个参数 当前页      第二个参数  每页的条数
        PageHelper.startPage(page,rows);
        Page<Specification> specificationsList = (Page<Specification>)specificationDao.selectByExample(specificationQuery);
        return new PageResult(specificationsList.getTotal(),specificationsList.getResult());
    }

    @Override
    public Specification findById(Long id) {
        return specificationDao.selectByPrimaryKey(id);
    }

    @Override
    public Specification findByName(String name) {
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        criteria.andSpecNameEqualTo(name);
        return specificationDao.selectByExample(specificationQuery).get(0);
    }

    @Override
    public int save(Specification specification) {
        return specificationDao.insert(specification);
    }

    @Override
    public int deleteById(Long id) {
        return specificationDao.deleteByPrimaryKey(id);
    }


}
