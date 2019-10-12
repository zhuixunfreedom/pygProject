package com.ylt.service;


import com.ylt.pojo.entity.GoodsEntity;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.good.Goods;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lijun
 * @date 2019/9/20 10:49
 */
public interface GoodsService  {
    void add(GoodsEntity goodsEntity);
    PageResult findPage(@RequestBody Goods goods, Integer page, Integer rows);
    // 回显
    GoodsEntity findOne(Long id);

    void updateStatus(Long[] ids, String status);

    void delete(Long[] ids);
}
