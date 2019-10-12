package com.ylt;

import com.ylt.dao.good.BrandDao;
import com.ylt.pojo.good.Brand;
import com.ylt.pojo.good.BrandQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author lijun
 * @date 2019/9/9 15:06
 */
//  需要spring 测试环境
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestBrandDao1 {
    @Autowired
    private BrandDao brandDao;
    @Test
    public  void testFindById(){
        Brand brand = brandDao.selectByPrimaryKey(1L);
        System.out.println(brand);
//        System.out.println("test");
    }
    @Test
    public  void testFindByAll(){
        List<Brand> brands = brandDao.selectByExample(null);
        System.out.println(brands);
    }
    // 条件
    @Test
    public void testFindBrandByWhere(){
        BrandQuery brandQuery = new BrandQuery();
        brandQuery.setDistinct(true);
        brandQuery.setOrderByClause("id desc");
        // 创建where 条件查询对象
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        criteria.andIdEqualTo(1L);
        criteria.andNameLike("%联%");
        criteria.andFirstCharLike("%L%");
        List<Brand> brands = brandDao.selectByExample(brandQuery);
        System.out.println(brands);
    }
}
