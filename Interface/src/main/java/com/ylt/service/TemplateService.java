package com.ylt.service;


import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.template.TypeTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/17 17:23
 */
public interface TemplateService {
     PageResult findPage(TypeTemplate template, Integer page, Integer rows);
     void add(TypeTemplate template);
     TypeTemplate findOne(Long id);
     void update(TypeTemplate template);
     void delete(Long[] ids);

    List<Map> findBySpecList(Long id);
}
