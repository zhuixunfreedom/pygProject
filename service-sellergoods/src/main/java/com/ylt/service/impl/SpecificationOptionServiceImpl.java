package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ylt.dao.specification.SpecificationDao;
import com.ylt.dao.specification.SpecificationOptionDao;
import com.ylt.pojo.specification.SpecificationOption;
import com.ylt.pojo.specification.SpecificationOptionQuery;
import com.ylt.service.SpecificationOptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationOptionServiceImpl implements SpecificationOptionService {
    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    @Override
    public List<SpecificationOption> findSpecificationOptionById(Long id) {
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
        return specificationOptionDao.selectByExample(specificationOptionQuery);
    }

    @Override
    public boolean save(List<SpecificationOption> specificationOptions) {
        int count = 0;
        for (SpecificationOption specificationOption : specificationOptions) {
            int insert = specificationOptionDao.insert(specificationOption);
            if (insert != 0) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int deleteByspecId(Long id) {
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
        return specificationOptionDao.deleteByExample(specificationOptionQuery);
    }


    @Override
    public List<Map> selectOptionList() {
        return specificationOptionDao.selectOptionList();
    }
}