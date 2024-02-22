package com.example.cloudBlogUser;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudblogcommon",
        "com.example.cloudBlogUser"
})
public class CloudBlogUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudBlogUserApplication.class, args);
    }

}
