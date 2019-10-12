package util;

import com.ylt.dao.item.ItemDao;
import com.ylt.pojo.item.Item;
import com.ylt.pojo.item.ItemQuery;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/27 17:00
 */
@Component
public class DataImportToSolr {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private ItemDao itemDao;
    public  void importItemDataToSolr(){
        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andStatusEqualTo("1");
        List<Item> items = itemDao.selectByExample(query);
        if(items!=null){
            for(Item item:items){
                //json格式的字符串
                String specJsonStr = item.getSpec();
                Map map = JSON.parseObject(specJsonStr,Map.class);
                item.setSpecMap(map);

            }
            solrTemplate.saveBeans(items);
            solrTemplate.commit();
        }
    }

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/application*.xml");
        DataImportToSolr bean =(DataImportToSolr)context.getBean("dataImportToSolr");
        bean.importItemDataToSolr();
    }
}
