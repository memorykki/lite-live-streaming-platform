package com.xinf.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xinf
 * @since 2021/9/7 16:43
 */
@Component
@ConfigurationProperties(prefix = "live-streaming-platform.filepath")
public class FilePathConstant {
    public String dynamicFilePath;
    public String dynamicFileUrl;
}
