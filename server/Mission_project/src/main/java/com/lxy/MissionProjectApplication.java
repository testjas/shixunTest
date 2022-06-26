package com.lxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lxy.mapper")
public class MissionProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionProjectApplication.class, args);
    }

}
