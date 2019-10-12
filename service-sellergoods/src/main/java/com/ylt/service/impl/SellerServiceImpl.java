package com.ylt.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.seller.SellerDao;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.seller.Seller;
import com.ylt.pojo.seller.SellerQuery;
import com.ylt.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lijun
 * @date 2019/9/18 15:51
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDao sellerDao;
    @Override
    public void add(Seller seller) {
       // 手动添加 未审核状态   手动初始化时间
        seller.setStatus("0");
        seller.setCreateTime(new Date());
        sellerDao.insertSelective(seller);
    }

    @Override
    public PageResult findPage(Seller seller, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        SellerQuery query = new SellerQuery();
        SellerQuery.Criteria criteria = query.createCriteria();
        if(seller!=null){
            if(seller.getStatus()!=null&&!"".equals(seller.getStatus())){
                criteria.andStatusEqualTo(seller.getStatus());
            }
            if(seller.getName()!=null&&!"".equals(seller.getName())){
                criteria.andNameLike("%"+seller.getName()+"%");
            }
            if(seller.getNickName()!=null&&!"".equals(seller.getNickName())){
                criteria.andNickNameLike("%"+seller.getNickName()+"%");
            }

        }
        Page<Seller> sellerList = (Page<Seller>) sellerDao.selectByExample(query);
        return new PageResult(sellerList.getTotal(),sellerList.getResult());
    }
// 数据回显
    @Override
    public Seller findOne(String id) {
        return sellerDao.selectByPrimaryKey(id);
    }
// 修改审核状态
    @Override
    public void updateStatus(String sellerId, String status) {
        Seller seller = new Seller();
        seller.setStatus(status);
        seller.setSellerId(sellerId);// 其它值null
        sellerDao.updateByPrimaryKeySelective(seller);
    }
}
