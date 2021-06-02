package com.yr.net.app.configure;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author dengbp
 * @ClassName WxConfig
 * @Description TODO
 * @date 6/1/21 1:31 PM
 */
@Component
public class WxConfig  implements WXPayConfig {

    private byte[] certData;

    private AppProperties appProperties;

    public WxConfig(AppProperties appProperties) throws Exception {
        this.appProperties = appProperties;
        String certPath = appProperties.getWx().getCertificate_path();
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return appProperties.getWx().getApp_id();
    }

    public String getMchID() {
        return appProperties.getWx().getMch_id();
    }

    public String getKey() {
        return appProperties.getWx().getApi_key();
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
