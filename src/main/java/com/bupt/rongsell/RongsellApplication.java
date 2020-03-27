package com.bupt.rongsell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.bupt.rongsell.dao")
public class RongsellApplication {

    public static void main(String[] args) {
        SpringApplication.run(RongsellApplication.class, args);
    }

}
