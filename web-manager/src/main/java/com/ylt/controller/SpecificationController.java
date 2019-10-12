package com.ylt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.entity.Result;
import com.ylt.pojo.specification.Specification;
import com.ylt.pojo.specification.SpecificationOption;
import com.ylt.service.SpecificationOptionService;
import com.ylt.service.SpecificationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;
    @Reference
    private SpecificationOptionService specificationOptionService;

    //    unicode码 转 字符串
    private static String unicodeToString(String unicode) {
        StringBuilder str = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            str.append((char) data);
        }
        return str.toString();
    }
    //    字符串 转 unicode码
    public static String stringToUnicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u").append(Integer.toHexString(c));
        }
        return unicode.toString();
    }

//    条件查询
    @RequestMapping(value = "/search",produces = "text/plain;charset=utf-8")
    public String search(String name,Integer page, Integer rows){
        if("".equals(name)||"undefined".equals(name)){
            name=null;
        }
        PageResult result;
        try{
            result = specificationService.search(name, page, rows);
            return JSONObject.fromObject(result).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }
    @RequestMapping(value = "/update",produces = "text/plain;charset=utf-8")
    public String update(Long id){
        List<Object> list = new ArrayList<>();
        //查询规格
        Specification specification = specificationService.findById(id);
        list.add(specification);
        //查询规格选项
        List<SpecificationOption> specificationOptions = specificationOptionService.findSpecificationOptionById(specification.getId());
        list.add(specificationOptions);
        return JSONArray.fromObject(list).toString();
    }
    @RequestMapping("/save")
    public Object save(String specification,String specificationOptions){

        Specification specificationData = JSON.parseObject(unicodeToString(specification), Specification.class);
        //id不为空，进行修改操作，先删除，再保存
        if(specificationData.getId()!=null){
            Specification specificationById = specificationService.findById(specificationData.getId());
            specificationService.deleteById(specificationById.getId());
            specificationOptionService.deleteByspecId(specificationById.getId());
        }
        //保存规格
        int saveSpecification = specificationService.save(specificationData);
        //获取保存规格的数据
        Specification specificationByName = specificationService.findByName(specificationData.getSpecName());

        List<SpecificationOption> list = JSON.parseArray(unicodeToString(specificationOptions), SpecificationOption.class);
        for (SpecificationOption specificationOption:list) {
            specificationOption.setSpecId(specificationByName.getId());
        }
        //保存规格选项
        boolean saveSpecificationOption = specificationOptionService.save(list);
        //判断保存结果
        if(saveSpecification!=0&&saveSpecificationOption){
            return JSONObject.fromObject(new Result(true,"添加数据成功"));
        }
        return JSONObject.fromObject(new Result(false,"添加数据失败"));
    }

//    删除
    @RequestMapping("/del")
    public Object del(Long[] ids){
        try {
            for (Long id:ids) {
                Specification specificationById = specificationService.findById(id);
                specificationService.deleteById(specificationById.getId());
                specificationOptionService.deleteByspecId(specificationById.getId());
            }
            return JSONObject.fromObject(new Result(true,"删除数据成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return JSONObject.fromObject(new Result(false,"删除数据失败"));
        }
    }

    // 获取模板下拉数据
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationOptionService.selectOptionList();
    }

}
