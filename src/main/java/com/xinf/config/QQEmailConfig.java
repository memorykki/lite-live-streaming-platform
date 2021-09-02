package com.xinf.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月02日 14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix="tencent.qq.email")
@Component
public class QQEmailConfig {
    private String hostname;
    private String from;
    private String name;
    private String username;
    private String password;
    private String subject;
}
