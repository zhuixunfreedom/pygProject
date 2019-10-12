package com.ylt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.good.Brand;
import com.ylt.service.BrandService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

//    @Autowired
    @Reference
    private BrandService brandService;

    @RequestMapping("/test")
    public void test(){
        System.out.println("测试");
    }
    @RequestMapping("/a")
    public String a(){
        System.out.println("测试"+brandService);
        return "ces";
    }
    @RequestMapping(value = "/findAll",produces = "text/plain;charset=utf-8")
    public String findAll(){
        try{
            List<Brand> list = brandService.findAll();
            return JSONArray.fromObject(list).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    @RequestMapping(value = "/findById",produces = "text/plain;charset=utf-8")
    public String findById(Long id){
        try {
            Brand brand = brandService.findById(id);
            return JSONObject.fromObject(brand).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }

    // 分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/findPage",produces = "text/plain;charset=utf-8")
    public String findPage(Integer page, Integer rows){
        try{
            PageResult result = brandService.findPage(page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 条件查询并分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/search",produces = "text/plain;charset=utf-8")
    public String search(Brand searchBrand,Integer page, Integer rows){
        PageResult result;
        if("undefined".equals(searchBrand.getName())||"".equals(searchBrand.getName())){
            searchBrand.setName(null);
        }
        if("undefined".equals(searchBrand.getFirstChar())||"".equals(searchBrand.getFirstChar())){
            searchBrand.setFirstChar(null);
        }
        try{
            result = brandService.search(searchBrand, page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 添加
    @RequestMapping("/save")
    public Object save(Brand brand){
        brand.setId(null);
        try{
            int save = brandService.save(brand);
            if(save!=0){
                return JSONObject.fromObject(new Result(true,"成功添加"+save+"条数据"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONObject.fromObject(new Result(false,"添加数据失败"));
    }
    //修改
    @RequestMapping("/update")
    public Object update(Brand brand){
        try{
            int update = brandService.update(brand);
            if(update!=0){
                return JSONObject.fromObject(new Result(true,"修改成功"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONObject.fromObject(new Result(false,"修改失败"));
    }
    //删除
    @RequestMapping("/delete")
    public Object delete(Long[] ids){
        int count = 0;
        for (Long id:ids) {
            try{
                brandService.delete(id);
            }catch (Exception e){
                count++;
                e.printStackTrace();
            }
        }
        return JSONObject.fromObject(new Result(false,"成功删除"+(ids.length-count)+"条数据,"+count+"条数据删除失败。"));
        }

    // 获取模板下拉数据
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }

}
