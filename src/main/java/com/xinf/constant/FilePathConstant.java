package com.xinf.constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;

/**
 * @author xinf
 * @since 2021/9/7 16:43
 */
@Component
@ConfigurationProperties(prefix = "live-streaming-platform.filepath")
@Slf4j
@Data
public class FilePathConstant implements InitializingBean {
    public String dynamicFilePath;
    public String dynamicFileUrl;
    public String commonFilePath;
    public String commonFileUrl;
    public String hdfsFilePath;

    @Value("${spring.profiles.active}")
    public String profile;

    @Override
    public String toString() {
        return "FilePathConstant{" +
                "dynamicFilePath='" + dynamicFilePath + '\'' +
                ", dynamicFileUrl='" + dynamicFileUrl + '\'' +
                ", banFilePath='" + commonFilePath + '\'' +
                ", banFileUrl='" + commonFileUrl + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (profile.equals("test")) {
            String path = URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "utf-8") + "static/file";
            log.info("get test path root: {}", path);
            setCommonFilePath(path + getCommonFilePath());
            setDynamicFilePath(path + getDynamicFilePath());
        }
    }
}
