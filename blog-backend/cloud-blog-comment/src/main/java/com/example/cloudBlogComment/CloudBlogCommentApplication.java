package com.example.cloudBlogComment;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudblogcommon",
        "com.example.cloudBlogComment"
})
public class CloudBlogCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudBlogCommentApplication.class, args);
    }

}
