package com.xavier.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@MapperScan({"com.xavier.base.dao"})
@EnableCaching(proxyTargetClass = true)
public class BaseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseWebApplication.class, args);
    }
}
