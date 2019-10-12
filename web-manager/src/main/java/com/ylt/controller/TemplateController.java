package com.ylt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.template.TypeTemplate;
import com.ylt.service.TemplateService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijun
 * @date 2019/9/17 17:19
 */
@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {
    @Reference
    private TemplateService templateService;
    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer rows){
        PageResult result = templateService.findPage(template, page, rows);
        return result;
    }
    //添加
    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template){
        try {
            templateService.add(template);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id){
        return templateService.findOne(id);
    }
    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplate template){
        try {
            templateService.update(template);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            templateService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }
}
