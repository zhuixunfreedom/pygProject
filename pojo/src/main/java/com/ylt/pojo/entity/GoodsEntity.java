package com.ylt.pojo.entity;



import com.ylt.pojo.good.Goods;
import com.ylt.pojo.good.GoodsDesc;
import com.ylt.pojo.item.Item;

import java.io.Serializable;
import java.util.List;

/**
 * @author lijun
 * @date 2019/9/20 16:25
 */
public class GoodsEntity implements Serializable {
    private Goods goods;
    private GoodsDesc goodsDesc;
    private List<Item> itemList;

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "goods=" + goods +
                ", goodsDesc=" + goodsDesc +
                ", itemList=" + itemList +
                '}';
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
