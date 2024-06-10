package com.github.moruke.wall.demo.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.github.moruke", "com.github.moruke.wall.bootstrap.api.auth", "com.github.moruke.wall.bootstrap.api.account"})
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@MapperScan({"com.github.moruke.wall.auth.dao.mapper", "com.github.moruke.wall.account.dao.mapper"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
