package com.ylt.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylt.dao.specification.SpecificationOptionDao;
import com.ylt.dao.template.TypeTemplateDao;
import com.ylt.pojo.entity.PageResult;
import com.ylt.pojo.specification.SpecificationOption;
import com.ylt.pojo.specification.SpecificationOptionQuery;
import com.ylt.pojo.template.TypeTemplate;
import com.ylt.pojo.template.TypeTemplateQuery;
import com.ylt.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/17 17:24
 */
@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao optionDao;
    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        if(template!=null){
            if(template.getName()!=null&&!"".equals(template.getName())){
                criteria.andNameLike("%"+template.getName()+"%");

            }
        }
        Page<TypeTemplate> templateList = (Page<TypeTemplate>) typeTemplateDao.selectByExample(query);
        return new PageResult(templateList.getTotal(),templateList.getResult());
    }

    @Override
    public void add(TypeTemplate template) {
        typeTemplateDao.insertSelective(template);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(TypeTemplate template) {
        typeTemplateDao.updateByPrimaryKeySelective(template);
    }

    @Override
    public void delete(Long[] ids) {
        if(ids!=null){
            for(Long id:ids){
                typeTemplateDao.deleteByPrimaryKey(id);
            }
        }

    }

    @Override
    public List<Map> findBySpecList(Long id) {
        // 1 根据模板id 查询模板对象
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        //2  从模板对象中获取规格集合函数    获取到是json 格式字符串
        String specIds = typeTemplate.getSpecIds();
        //3 将json 格式转 List集合
        List<Map> maps = JSON.parseArray(specIds, Map.class);
        //4 遍历集合
        if(maps!=null){
            for(Map map:maps){
                //5  遍历过程中  规格的ID   查询规格选项集合数据
                Long specId= Long.parseLong(String.valueOf(map.get("id")));
                // 6 将规格选项数据再封装到规格数据中一起返回
                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(specId);
                List<SpecificationOption> optionList = optionDao.selectByExample(query);
                map.put("options",optionList);

            }
        }

        return maps;
    }
}
