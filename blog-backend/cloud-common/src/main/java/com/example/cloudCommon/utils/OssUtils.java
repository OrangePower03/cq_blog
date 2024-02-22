package com.example.cloudCommon.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.example.cloudCommon.enums.AppHttpCodeEnum;
import com.example.cloudCommon.exception.SystemException;
import io.jsonwebtoken.lang.Strings;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class OssUtils {
    private static final String bucketName = "cqyyds";                      // 阿里云的容器名
    private static final String transferProtocol = "https://";              // 超文本协议
    private static final String address = "oss-cn-hangzhou.aliyuncs.com";   // 华东地区地址

    // 外链的前缀
    private static final String prefix = transferProtocol+bucketName+"."+address+"/";
    // 图片类型匹配
    private static final List<String> IMAGE_SUFFIX=List.of(".jpg",".png");

    private OssUtils() {
    }

    public static String pushImage(MultipartFile image){
        //华东地区的ip地址，
        String endpoint = transferProtocol+address;
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = null;
        try {
            credentialsProvider = CredentialsProviderFactory
                    .newEnvironmentVariableCredentialsProvider();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            throw new RuntimeException(e);
        }
        String fileName=image.getOriginalFilename();
        if(!isImage(fileName)) {
            throw new SystemException(AppHttpCodeEnum.IMAGE_SUFFIX_ERROR);
        }
        LocalDate currentDate = LocalDate.now();

        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        UUID uuid = UUID.randomUUID();
        String imageName = year+"/"+month+"/"+day+"/"+uuid+"_"+fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            ossClient.putObject(bucketName, imageName, image.getInputStream());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return prefix+imageName;
    }

    private static boolean isImage(String fileName) {
        if(!Strings.hasText(fileName)) {
            return false;
        }
        int pointIndex = fileName.lastIndexOf(".");
        String ext = fileName.substring(pointIndex);
        return IMAGE_SUFFIX.contains(ext);
    }
}
