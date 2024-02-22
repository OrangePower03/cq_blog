package com.example.cloudAdminLink;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {
        "com.example.cloudAdminLink",
        "com.example.cloudAdminCommon"
})
public class CloudAdminLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudAdminLinkApplication.class, args);
    }

}
