package com.xinf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xinf.dao")
public class LiteLiveStreamingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteLiveStreamingPlatformApplication.class, args);
    }

}
