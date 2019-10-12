package com.ylt.controller;


import com.ylt.service.SearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/30 14:49
 */
@RestController
@RequestMapping("/itemsearch")
public class SearchController {
    @Reference
    private SearchService searchService;
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map paramMap){
        Map<String, Object> resultMap = searchService.search(paramMap);
        return resultMap;

    }
}
