package com.xinf.util;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.xinf.config.TencentCloudSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月02日 8:50
 */
@Component
public class SmsUtil {
    @Autowired
    TencentCloudSmsConfig tencentCloudSmsConfig;
    private int code;
    /**
     *
     * @param phone  电话，不带区号
     *
     * @return 发送成功返回六位正整数，失败返回-1
     */
    public int send(String phone) {
        code = (int)((Math.random()*9+1)*100000);
        SendSmsResponse resp = null;
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(tencentCloudSmsConfig.getSecretId(), tencentCloudSmsConfig.getSecretKey());
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(tencentCloudSmsConfig.getEndpoint());
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, tencentCloudSmsConfig.getRegion(), clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppId(tencentCloudSmsConfig.getSmsSdkAppId());
            req.setSignName(tencentCloudSmsConfig.getSignName());
            req.setTemplateId(tencentCloudSmsConfig.getTemplateId());

            String[] templateParamSet1 = {String.valueOf(code)};
            req.setTemplateParamSet(templateParamSet1);
            resp = client.SendSms(req);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

        if(resp.getSendStatusSet()[0] != null && resp.getSendStatusSet()[0].getCode().equals("Ok")){
            return code;
        }
        return -1;
    }
}