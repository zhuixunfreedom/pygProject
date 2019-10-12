package com.ylt.service;

import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.specification.Specification;
import com.ylt.pojo.specification.SpecificationOption;

import java.util.List;
import java.util.Map;

public interface SpecificationService {

    PageResult search(String name, Integer page, Integer rows);

    Specification findById(Long id);

    Specification findByName(String name);


    int save(Specification specification);

    int deleteById(Long id);
}
