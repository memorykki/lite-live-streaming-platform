package com.xinf.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xinf
 * @since 2021/9/2 14:11
 */
@Component
@ConfigurationProperties(prefix = "live-streaming-platform.jwt")
@Data
public class SecurityProperties {
    public int expire;
    public String salt;
}
