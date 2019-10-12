package com.ylt.controller;

import com.ylt.pojo.entity.Result;
import com.ylt.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UpLoadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) throws Exception{
        try {
            //加载配置文件到客户端工具类
            FastDFSClient fastDFS = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            //上传文件返回文件的保存路径和文件名
            String path = fastDFS.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            return new Result(true,FILE_SERVER_URL+path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
}
