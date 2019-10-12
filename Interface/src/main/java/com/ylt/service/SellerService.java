package com.ylt.service;


import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.seller.Seller;

/**
 * @author lijun
 * @date 2019/9/18 15:49
 */
public interface SellerService {
     void add(Seller seller);
    // 分页查询
     PageResult findPage(Seller seller, Integer page, Integer rows);
    // 回显
      Seller findOne(String id);
    // 修改状态
     void updateStatus(String sellerId, String status);
}
