package com.xinf;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xinf.dao")
@Slf4j
public class LiteLiveStreamingPlatformApplication {


    public static void main(String[] args) {
        SpringApplication.run(LiteLiveStreamingPlatformApplication.class, args);
        log.info("application successed");
    }

}
