package com.ylt.service;

import com.ylt.pojo.item.Item;
import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/30 14:56
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map<String, Object> search(Map paramMap) {
        // 获得关键字
        String keywords = String.valueOf(paramMap.get("keywords"));
        // 获得当前页
        Integer pageNo =Integer.parseInt(String.valueOf(paramMap.get("pageNo")));
        // 每页条数
        Integer pageSize =Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
        //创建查询对象
        Query query = new SimpleQuery();
        // 创建查询对象的条件
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        query.addCriteria(criteria);
        if(pageNo==null||pageNo<=0){
            pageNo=1;
        }
        //当前页-1* pageSize
        Integer start = (pageNo - 1) * pageSize;
        // 从第几条开始
        query.setOffset(start);
        // 设置每页查询多少条数据
        query.setRows(pageSize);
        // 查询并返回结果
        ScoredPage<Item> items = solrTemplate.queryForPage(query, Item.class);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows",items.getContent());
        // 总页数
        resultMap.put("totalPages",items.getTotalPages());
        // 总条数
        resultMap.put("total",items.getTotalElements());


        return resultMap;
    }
}
