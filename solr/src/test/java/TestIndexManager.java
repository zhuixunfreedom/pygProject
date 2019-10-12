import bean.Item;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author lijun
 * @date 2019/9/27 14:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-solr.xml")
public class TestIndexManager {
     @Test
    public void testIndexCreateAndUpdate()throws Exception{
        // 连solr服务器
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.200.128:8080/solr");
        // 创建文档对象
        SolrInputDocument doc = new SolrInputDocument();
        // 放域
        doc.addField("id","009");
        doc.addField("title","bobo7");
        doc.addField("price","5000");
        solrServer.add(doc);
        solrServer.commit();
    }
    @Test
    public void testIndexDelete()throws Exception{
        // 连solr服务器
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.200.128:8080/solr");
       //  删除一个
        //solrServer.deleteById("007");
        // del   all
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }


    @Autowired
    private SolrTemplate solrTemplate;
    @Test
    public void testIndexCreate(){
        ArrayList<Item> itemList = new ArrayList<Item>();
        for(long i=3;i<100;i++){
            Item item = new Item();
            item.setId(i);
            item.setTitle("华为手机"+i);
            item.setCategory("手机");
            item.setPrice(new BigDecimal("6666"));
            item.setBrand("华为");
            itemList.add(item);
        }

        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
    }
    @Test
    public void testDelete(){
        //solrTemplate.deleteById("2");
        SimpleQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }
}
