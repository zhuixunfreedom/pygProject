package com.ylt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.item.ItemCat;
import com.ylt.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijun
 * @date 2019/9/19 14:23
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService catService;
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }
    // 查询类别
    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return catService.findAll();
    }
}
