package com.github.moruke.wall.demo.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.github.moruke", "com.github.moruke.wall.bootstrap.api.auth"})
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@MapperScan({"com.github.moruke.*.*.dao"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
