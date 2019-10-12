package cn.lijun;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijun
 * @date 2019/9/30 15:59
 */
public class TestFreemarker{
    public static void main(String[] args)throws Exception{
        // 1 创建模板引擎  初始化对象
        Configuration conf = new Configuration();
        //2  加载模板目录所在位置
        conf.setDirectoryForTemplateLoading(new File("D:\\360Downloads\\java\\pyg-parent\\freemarkerDemo\\src\\main\\resources\\ftl"));
        //3 加载 模板对象
        Template template = conf.getTemplate("test.ftl");
        //4 模拟假数据   这个数据放到模板中
        Map<String, Object> rootMap = new HashMap<String, Object>();
        rootMap.put("name","bobo");
        rootMap.put("message","来了老弟");
        //5 创建io流  流中指定文件的首次被保护位置  和文件名
        Writer out = new FileWriter(new File("D:\\360Downloads\\java\\pyg-parent\\freemarkerDemo\\src\\main\\webapp\\laile.html"));
        //  6 生成
        template.process(rootMap,out);
        // 7  关流
        out.close();
    }
}
