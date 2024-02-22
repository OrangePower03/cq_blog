package com.example.cloudbloglink;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudblogcommon",
        "com.example.cloudbloglink"
})
public class CloudBlogLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudBlogLinkApplication.class, args);
    }

}
