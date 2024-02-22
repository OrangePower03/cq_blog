package com.example.cloudAdminArticle;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudAdminArticle",
        "com.example.cloudAdminCommon"
})
public class CloudAdminArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAdminArticleApplication.class, args);
    }

}
