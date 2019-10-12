import com.ylt.pojo.item.Item;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author lijun
 * @date 2019/9/27 14:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-solr.xml")
public class TestSearch {
    @Test
    public void testIndexSearch()throws Exception{
        // 连solr服务器
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.200.128:8080/solr");
        SolrQuery query = new SolrQuery();
        // 设置查询条件
        query.setQuery("*:*");
        // 查询并返回响应
        QueryResponse queryResponse = solrServer.query(query);
        // 从queryResponse  获得结果
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询到"+results.getNumFound()+"条数据");
        for(SolrDocument result:results){
            System.out.println("id:"+result.get("id"));
            System.out.println("title:"+result.get("title"));
            System.out.println("price:"+result.get("price"));
        }
    }

    @Autowired
    private SolrTemplate solrTemplate;
    @Test
    public void testSearch(){
        //创建查询对象
        SimpleQuery query = new SimpleQuery("*:*");
        query.setOffset(3);
        query.setRows(20);
        // 查询并返回的结果
        ScoredPage<Item> items = solrTemplate.queryForPage(query, Item.class);
        int totalPages = items.getTotalPages();
        long totalElements = items.getTotalElements();
        List<Item> content = items.getContent();
        int numberOfElements = items.getNumberOfElements();
        System.out.println(totalElements);
    }
}
