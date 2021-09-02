package com.xinf.util;

import com.xinf.config.QQEmailConfig;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月02日 15:34
 */
@Component
public class EmailUtil {

    @Autowired
    QQEmailConfig qqEmailConfig;
    /**
     *
     * @param recvAddress：邮箱地址
     *
     * @return 发送成功返回六位正整数，失败返回-1
     */
    public int send(String recvAddress){
        int code = (int)((Math.random()*9+1)*100000);
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(qqEmailConfig.getHostname());
            email.setCharset("UTF-8");
            email.addTo(recvAddress);
            email.setFrom(qqEmailConfig.getFrom(), qqEmailConfig.getName());
            email.setAuthentication(qqEmailConfig.getUsername(), qqEmailConfig.getPassword());

            email.setSubject(qqEmailConfig.getSubject());
            email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + code);
            String send = email.send();
            return code;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
