package com.xinf;

import com.xinf.util.cache.MybatisRedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class LiteLiveStreamingPlatformApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(LiteLiveStreamingPlatformApplication.class, args);
        prepared(application);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path").trim();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application LiteLiveStreamingPlatform is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Druid文档: \thttp://" + ip + ":" + port + path + "/druid/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/swagger-ui.html#/\n" +
                "----------------------------------------------------------");
    }

    private static void prepared(ConfigurableApplicationContext application) {
        // 配置mybatisRedisCache中对redis的依赖
        MybatisRedisCache.setRedisTemplate((RedisTemplate<String, Object>) application.getBean("redisTemplate"));
    }
}
