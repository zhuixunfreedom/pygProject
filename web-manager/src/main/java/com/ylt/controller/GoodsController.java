package com.ylt.controller;




import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.entity.GoodsEntity;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.good.Goods;
import com.ylt.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    @RequestMapping("/add")
    public Result add(@RequestBody GoodsEntity goodsEntity) {
        try {
            //获取登录用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            //设置这个商品添加的用户名, 也就是卖家id
            goodsEntity.getGoods().setSellerId(userName);

            goodsService.add(goodsEntity);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }
    // 分页查询
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, Integer page, Integer rows){
        // 获取当前登录用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(userName);
//        System.out.println(goods);
        PageResult result = goodsService.findPage(goods, page, rows);
        return result;
    }
    @RequestMapping("/findOne")
    public GoodsEntity findOne(Long id){
        return goodsService.findOne(id);

    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try{
            goodsService.updateStatus(ids,status);
            return new Result(true, "修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败!");
        }
    }
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try{
            goodsService.delete(ids);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }
}
