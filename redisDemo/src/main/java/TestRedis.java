import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-redis.xml"})
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testSetString(){
        redisTemplate.boundValueOps("testStr").set("字符");
    }
    @Test
    public void testGetString(){
        String testStr = (String)redisTemplate.boundValueOps("testStr").get();
        System.out.println(testStr);
    }
    @Test
    public void testSetHash(){
        redisTemplate.boundHashOps("testHash").put("HashKey1","Value1");
        redisTemplate.boundHashOps("testHash").put("HashKey2","Value2");
        redisTemplate.boundHashOps("testHash").put("HashKey3","Value3");
    }
    @Test
    public void testGetHash(){
        Map<String,String> testHash = redisTemplate.boundHashOps("testHash").entries();
        Set<Map.Entry<String, String>> entries = testHash.entrySet();
        for (Map.Entry<String,String> entry:entries) {
            System.out.println("Key: "+entry.getKey());
            System.out.println("Value: "+entry.getValue());
        }
    }
    @Test
    public void testSetList(){
        redisTemplate.boundListOps("testList").leftPush("list1");
    }
    @Test
    public void testGetList(){
        String testList = (String)redisTemplate.boundListOps("testList").index(0);
        System.out.println(testList);
    }
    @Test
    public void testSetSet(){
        redisTemplate.boundSetOps("testSet").add("set1","set2","set3");
    }
    @Test
    public void testGetSet(){
        redisTemplate.boundSetOps("testSet").members().forEach(v -> System.out.println("Set中的值："+v));
    }
    @Test
    public void testSetZSet(){
        redisTemplate.boundZSetOps("testZSet").add("ZSet1",1);
        redisTemplate.boundZSetOps("testZSet").add("ZSet2",2);
        redisTemplate.boundZSetOps("testZSet").add("ZSet3",3);
    }
    @Test
    public void testGetZSet(){
        redisTemplate.boundZSetOps("testZSet").range(0,-1).forEach(v -> System.out.println("ZSet中的值："+v));
    }
}
