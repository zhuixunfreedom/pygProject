package cn.lijun.test;


import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * @author lijun
 * @date 2019/9/19 17:28
 */
public class TestFastDFS {
    public static void main(String[] args)throws Exception {
        //1 读取配置文件
        ClientGlobal.init("D:\\360Downloads\\java\\pyg-parent\\fastDFSDemo\\src\\main\\resources\\fdfs_client.conf");
        //2 创建管理端对象
        TrackerClient trackerClient = new TrackerClient();
        //3 通过管理端对象获得链接
        TrackerServer connection = trackerClient.getConnection();
        //4 创建 存储端文件‘
        StorageClient1 storageClient = new StorageClient1(connection,null);
        //创建文件属性信息数组
        NameValuePair[] meta_list=new NameValuePair[3];
        meta_list[0]=new NameValuePair("fileName","5");
        meta_list[1]=new NameValuePair("extName","jpeg");
        meta_list[2]=new NameValuePair("zuozhe","ylt");
        //5上传文件
        String path = storageClient.upload_file1("D:\\5.jpeg","jpeg",meta_list);
        System.out.println(path);
    }

}
