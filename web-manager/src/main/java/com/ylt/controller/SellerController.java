package com.ylt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.seller.Seller;
import com.ylt.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijun
 * @date 2019/9/18 16:10
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    // 分页搜索
    @RequestMapping("/search")
    public PageResult search(@RequestBody Seller seller, Integer page, Integer rows){
        PageResult result = sellerService.findPage(seller, page, rows);
        return result;
    }
    //  数据回显
    @RequestMapping("/findOne")
    public  Seller findOne(String id){
        return sellerService.findOne(id);
    }
    //  改变商家的审核状态
    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId, String status){
        try{
            sellerService.updateStatus(sellerId,status);
            return new Result(true,"状态修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"状态修改失败");
        }
    }
}
