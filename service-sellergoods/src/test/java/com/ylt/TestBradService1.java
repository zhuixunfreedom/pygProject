package com.ylt;

import com.ylt.pojo.good.Brand;
import com.ylt.service.BrandService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestBradService1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        BrandService bean = context.getBean(BrandService.class);
        List<Brand> all = bean.findAll();
        System.out.println(all);
    }
}
