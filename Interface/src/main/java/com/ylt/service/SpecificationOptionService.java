package com.ylt.service;

import com.ylt.pojo.specification.SpecificationOption;

import java.util.List;
import java.util.Map;

public interface SpecificationOptionService {
    List<SpecificationOption> findSpecificationOptionById(Long id);

    boolean save(List<SpecificationOption> specificationOptions);

    int deleteByspecId(Long id);

    // 查询所有
    List<Map> selectOptionList();
}
