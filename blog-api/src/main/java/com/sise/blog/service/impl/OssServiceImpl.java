package com.sise.blog.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.sise.blog.service.OssService;
import com.sise.blog.utils.FileConstantUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description:
 * @Author: xzw
 * @Date: 2023/2/11 20:23
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endPoint = FileConstantUtils.END_POIND;
        String accessKeyId = FileConstantUtils.ACCESS_KEY_ID;
        String accessKeySecret = FileConstantUtils.ACCESS_KEY_SECRET;
        String bucketName = FileConstantUtils.BUCKET_NAME;

        try {
            // 创建OSS实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();
            //1 在文件名称里面添加随机唯一的值，防止重复覆盖   fs-fsdfs-fsdf
            String uuid = UUID.randomUUID().toString().replace("-", "");
            // yuy76t5rew01.jpg
            fileName = uuid + fileName;
            //2 把文件按照日期进行分类
            //获取当前日期
            //   2023/2/11
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            //  2023/2/11/jfdijfi313401.jpg
            fileName = datePath + "/" + fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://edu-95278.oss-cn-shenzhen.aliyuncs.com/1.jpg
            String url = "https://"+bucketName+"."+endPoint+"/"+fileName;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
