package com.bupt.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// DiscoveryClient是spring cloud 官方提供，可以集成大部分client（不仅仅是EurekaClient）

/**
 * DiscoveryClient是spring cloud 官方提供，可以集成大部分注册中心（不仅仅是EurekaClient）
 * 因此这里用   {@link @EnableDiscoveryClient  而不用{@link @EnableEurekaClient}
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
