package com.ylt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.entity.GoodsEntity;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.service.ContentCategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

//    @Autowired
    @Reference
    private ContentCategoryService contentCategoryService;


    @RequestMapping(value = "/findAll",produces = "text/plain;charset=utf-8")
    public String findAll(){
        try{
            List<ContentCategory> list = contentCategoryService.findAll();
            return JSONArray.fromObject(list).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    @RequestMapping(value = "/findById",produces = "text/plain;charset=utf-8")
    public String findById(Long id){
        try {
            ContentCategory contentCategory = contentCategoryService.findById(id);
            return JSONObject.fromObject(contentCategory).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }

    // 分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/findPage",produces = "text/plain;charset=utf-8")
    public String findPage(Integer page, Integer rows){
        try{
            PageResult result = contentCategoryService.findPage(page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 条件查询并分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/search",produces = "text/plain;charset=utf-8")
    public String search(ContentCategory searchContentCategory,Integer page, Integer rows){
        PageResult result;
        try{
            result = contentCategoryService.search(searchContentCategory, page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 添加
    @RequestMapping("/save")
    public Object save(ContentCategory contentCategory){
        try{
            int save = contentCategoryService.save(contentCategory);
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
    public Object update(ContentCategory contentCategory){
        try{
            int update = contentCategoryService.update(contentCategory);
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
                contentCategoryService.delete(id);
            }catch (Exception e){
                count++;
                e.printStackTrace();
            }
        }
        return JSONObject.fromObject(new Result(false,"成功删除"+(ids.length-count)+"条数据,"+count+"条数据删除失败。"));
        }
    @RequestMapping("/findOne")
    public ContentCategory findOne(Long id){
        return contentCategoryService.findOne(id);

    }

}
