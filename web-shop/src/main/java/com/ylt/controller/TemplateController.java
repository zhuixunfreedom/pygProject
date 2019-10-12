package com.ylt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.template.TypeTemplate;
import com.ylt.service.TemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/17 17:19
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {
    @Reference
    private TemplateService templateService;

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return templateService.findOne(id);
    }
    @RequestMapping("/findBySpecList")
    // 根据模板id 查询规格和对应规
    public List<Map> findBySpecList(Long id){
        List<Map> list = templateService.findBySpecList(id);
        return list;
    }

}
