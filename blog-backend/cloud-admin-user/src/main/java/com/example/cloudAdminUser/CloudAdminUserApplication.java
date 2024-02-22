package com.example.cloudAdminUser;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudAdminUser",
        "com.example.cloudAdminCommon"
})
public class CloudAdminUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAdminUserApplication.class, args);
    }

}
