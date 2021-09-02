package com.xinf;

import com.xinf.config.TencentCloudSmsConfig;
import com.xinf.util.EmailUtil;
import com.xinf.util.SmsUtil;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LiteLiveStreamingPlatformApplicationTests {
    @Autowired
    SmsUtil smsUtil;
    @Autowired
    EmailUtil emailUtil;
    @Test
    void contextLoads() {
//        smsUtil.send("18791763056");
        int send = emailUtil.send("2426412613@qq.com");
        System.out.println(send);
    }
}
