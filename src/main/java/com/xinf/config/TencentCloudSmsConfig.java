package com.xinf.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月02日 14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix="tencent.cloud.sms")
@Component
public class TencentCloudSmsConfig {
    private String SecretId;
    private String SecretKey;
    private String Endpoint;
    private String Region;
    private String SmsSdkAppId;
    private String SignName;
    private String TemplateId;
}
