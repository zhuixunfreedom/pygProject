package com.ylt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.seller.Seller;
import com.ylt.service.SellerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijun
 * @date 2019/9/18 15:47
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("/add")
    public Result add(@RequestBody Seller seller){
        try {
            sellerService.add(seller);
            return new Result(true,"注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"注册失败");
        }
    }
    //  数据回显
    @RequestMapping("/findOne")
    public  Seller findOne(String id){
        return sellerService.findOne(id);
    }
}
