package com.example.cloudblogarticle;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo(scanBasePackages = "com.example.cloudblogarticle.serviceImpl")
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudblogcommon",
        "com.example.cloudblogarticle"
})
public class CloudBlogArticleApplication {

    public static void main(String[] args) {
//        BasicConfigurator.configure();
        SpringApplication.run(CloudBlogArticleApplication.class, args);
    }

}
