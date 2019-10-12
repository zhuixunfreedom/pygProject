package com.ylt.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ylt.dao.item.ItemCatDao;
import com.ylt.pojo.item.ItemCat;
import com.ylt.pojo.item.ItemCatQuery;
import com.ylt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * @author lijun
 * @date 2019/9/19 14:26
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatDao catDao;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = catDao.selectByExample(query);


        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return catDao.selectByExample(null);
    }
}
