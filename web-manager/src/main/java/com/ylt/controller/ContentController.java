package com.ylt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.ad.Content;
import com.ylt.pojo.ad.ContentCategory;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.service.ContentCategoryService;
import com.ylt.service.ContentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

//    @Autowired
    @Reference
    private ContentService contentService;


    @RequestMapping(value = "/findAll",produces = "text/plain;charset=utf-8")
    public String findAll(){
        try{
            List<Content> list = contentService.findAll();
            return JSONArray.fromObject(list).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    @RequestMapping(value = "/findById",produces = "text/plain;charset=utf-8")
    public String findById(Long id){
        try {
            Content content = contentService.findById(id);
            return JSONObject.fromObject(content).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }

    // 分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/findPage",produces = "text/plain;charset=utf-8")
    public String findPage(Integer page, Integer rows){
        try{
            PageResult result = contentService.findPage(page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 条件查询并分页   page  当前页       rows   每页的条数
    @RequestMapping(value = "/search",produces = "text/plain;charset=utf-8")
    public String search(Content searchContent,Integer page, Integer rows){
        PageResult result;
        try{
            result = contentService.search(searchContent, page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    // 添加
    @RequestMapping("/add")
    public Object save(@RequestBody Content content){
        try{
            content.setStatus("0");
            int save = contentService.save(content);
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
    public Object update(@RequestBody Content content){
        try{
            if(content.getId()!=null){
                contentService.delete(content.getId());
            }
            int save = contentService.save(content);
            if(save!=0){
                return JSONObject.fromObject(new Result(true,"成功添加"+save+"条数据"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONObject.fromObject(new Result(false,"添加数据失败"));
    }
    //删除
    @RequestMapping("/delete")
    public Object delete(Long[] ids){
        int count = 0;
        for (Long id:ids) {
            try{
                contentService.delete(id);
            }catch (Exception e){
                count++;
                e.printStackTrace();
            }
        }
        return JSONObject.fromObject(new Result(false,"成功删除"+(ids.length-count)+"条数据,"+count+"条数据删除失败。"));
        }
    @RequestMapping("/findOne")
    public Content findOne(Long id){
        return contentService.findOne(id);

    }

}
