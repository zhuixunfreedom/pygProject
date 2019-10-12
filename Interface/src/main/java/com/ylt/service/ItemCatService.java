package com.ylt.service;


import com.ylt.pojo.item.ItemCat;

import java.util.List;

/**
 * @author lijun
 * @date 2019/9/19 14:26
 */
public interface ItemCatService {
     List<ItemCat> findByParentId(Long parentId);
     ItemCat findOne(Long id);

     List<ItemCat> findAll();
}
