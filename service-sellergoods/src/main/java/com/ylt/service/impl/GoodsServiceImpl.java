package com.ylt.service.impl;

import com.github.pagehelper.Page;
import com.ylt.dao.good.BrandDao;
import com.ylt.dao.good.GoodsDao;
import com.ylt.dao.good.GoodsDescDao;
import com.ylt.dao.item.ItemCatDao;
import com.ylt.dao.item.ItemDao;
import com.ylt.dao.seller.SellerDao;
import com.ylt.pojo.entity.GoodsEntity;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.good.Brand;
import com.ylt.pojo.good.Goods;
import com.ylt.pojo.good.GoodsDesc;
import com.ylt.pojo.good.GoodsQuery;
import com.ylt.pojo.item.Item;
import com.ylt.pojo.item.ItemCat;
import com.ylt.pojo.item.ItemQuery;
import com.ylt.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.ylt.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao descDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private SellerDao sellerDao;


    @Override
    public void add(GoodsEntity goodsEntity) {
        /**
         * 1. 保存商品对象
         */
        //刚添加的商品状态默认为0 未审核
        goodsEntity.getGoods().setAuditStatus("0");
        goodsDao.insertSelective(goodsEntity.getGoods());
        System.out.println(goodsEntity.getGoods());

        /**
         * 2. 保存商品详情对象
         */
        //商品主键作为商品详情的主键
        goodsEntity.getGoodsDesc().setGoodsId(goodsEntity.getGoods().getId());
        descDao.insertSelective(goodsEntity.getGoodsDesc());

        /**
         * 3. 保存库存集合对象
         */
        insertItem(goodsEntity);
    }



    /**
     * 使用goodsEntity实体类中的数据初始化, item库存对象中的属性值
     * @param goodsEntity
     * @param item
     * @return
     */
    private Item setItemValue(GoodsEntity goodsEntity, Item item) {
        //商品id
        item.setGoodsId(goodsEntity.getGoods().getId());
        //创建时间
        item.setCreateTime(new Date());
        //更新时间
        item.setUpdateTime(new Date());
        //库存状态, 默认为0, 未审核
        item.setStatus("0");
        //分类id, 库存使用商品的第三级分类最为库存分类
        item.setCategoryid(goodsEntity.getGoods().getCategory3Id());
        //分类名称
        ItemCat itemCat = catDao.selectByPrimaryKey(goodsEntity.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        //品牌名称
        Brand brand = brandDao.selectByPrimaryKey(goodsEntity.getGoods().getBrandId());
        item.setBrand(brand.getName());
        //卖家名称
        Seller seller = sellerDao.selectByPrimaryKey(goodsEntity.getGoods().getSellerId());
        item.setSeller(seller.getName());
        //示例图片
        String itemImages = goodsEntity.getGoodsDesc().getItemImages();
        List<Map> maps = JSON.parseArray(itemImages, Map.class);
        if (maps != null && maps.size() > 0) {
            String url = String.valueOf(maps.get(0).get("url"));
            item.setImage(url);
        }
        return item;
    }

    /**
     * 保存库存数据
     */
    public void insertItem(GoodsEntity goodsEntity) {
        if ("1".equals(goodsEntity.getGoods().getIsEnableSpec())) {
            //勾选规格复选框, 有库存数据
            if (goodsEntity.getItemList() != null) {
                for (Item item : goodsEntity.getItemList()) {
                    //库存标题, 由商品名 + 规格组成具体的库存标题, 供消费者搜索使用, 可以搜索的更精确
                    String title = goodsEntity.getGoods().getGoodsName();
                    //从库存对象中获取前端传入的json格式规格字符串, 例如: {"机身内存":"16G","网络":"联通3G"}
                    String specJsonStr = item.getSpec();
                    //将json格式字符串转换成对象
                    Map speMap = JSON.parseObject(specJsonStr, Map.class);
                    //获取map中的value集合
                    Collection<String> values = speMap.values();
                    for (String value : values) {
                        title += " " + value;
                    }
                    item.setTitle(title);

                    //设置库存对象的属性值
                    setItemValue(goodsEntity, item);

                    itemDao.insertSelective(item);
                }
            }
        } else {
            //没有勾选复选框, 没有库存数据, 但是我们需要初始化一条, 不然前端有可能报错
            Item item = new Item();
            //价格
            item.setPrice(new BigDecimal("99999999999"));
            //库存量
            item.setNum(0);
            //初始化规格
            item.setSpec("{}");
            //标题
            item.setTitle(goodsEntity.getGoods().getGoodsName());
            //设置库存对象的属性值
            setItemValue(goodsEntity, item);
            itemDao.insertSelective(item);
        }
    }
    // 查询商家自己的所有商品  带分页
    @Override
    public PageResult findPage(Goods goods, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        GoodsQuery query = new GoodsQuery();
        GoodsQuery.Criteria criteria = query.createCriteria();
        if(goods!=null){
            if(goods.getGoodsName()!=null&&!"".equals(goods.getGoodsName())){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
                if(goods.getAuditStatus()!=null&&!"".equals(goods.getAuditStatus())){
                    criteria.andAuditStatusEqualTo(goods.getAuditStatus());
                }
            }
//            if(goods.getSellerId()!=null&&!"".equals(goods.getSellerId())&&!"admin".equals(goods.getSellerId())&&!"wc".equals(goods.getSellerId())){
//                criteria.andSellerIdEqualTo(goods.getSellerId());
//            }
        }
        Page<Goods> goods1 = (Page<Goods>)goodsDao.selectByExample(query);

        return new PageResult(goods1.getTotal(),goods1.getResult());
    }

    @Override
    public GoodsEntity findOne(Long id) {
        Goods goods = goodsDao.selectByPrimaryKey(id);
        GoodsDesc goodsDesc = descDao.selectByPrimaryKey(id);
        // 根据商品的id   查询库存
        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<Item> items = itemDao.selectByExample(query);
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoods(goods);
        goodsEntity.setGoodsDesc(goodsDesc);
        goodsEntity.setItemList(items);
        return goodsEntity;
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id:ids) {
            Goods goods = goodsDao.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsDao.updateByPrimaryKey(goods);
        }
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id:ids) {
            goodsDao.deleteByPrimaryKey(id);
            descDao.deleteByPrimaryKey(id);
            ItemQuery itemQuery = new ItemQuery();
            itemQuery.createCriteria().andGoodsIdEqualTo(id);
            itemDao.deleteByExample(itemQuery);
        }
    }
}
