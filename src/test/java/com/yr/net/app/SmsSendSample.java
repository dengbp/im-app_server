package com.yr.net.app;


import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;


/**
 * @author dengbp
 * @ClassName SmsSendSample
 * @Description TODO
 * @date 6/4/21 7:02 PM
 */
public class SmsSendSample {

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = SmsSendSample.createClient("LTAI5t7qRg3qvN9hpqSaPPga", "jENv12H4sU1PJz0QfrdC0XHYbbjSfL");
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers("13530051353");
        sendSmsRequest.setTemplateCode("123456");
        sendSmsRequest.setSignName("深圳伊人网");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
        System.out.println("发送短信验证码...");
    }
}
