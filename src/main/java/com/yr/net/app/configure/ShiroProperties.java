package com.yr.net.app.configure;

import lombok.Data;

/**
 * @author dengbp
 */
@Data
public class ShiroProperties {

    private String anonUrl;
    private String loginUrl;

    /**
     * token默认有效时间 1小时
     */
    private Long jwtTimeOut = 60*60*1000L;


    public String getAnonUrl() {
        return anonUrl;
    }

    public void setAnonUrl(String anonUrl) {
        this.anonUrl = anonUrl;
    }

    public Long getJwtTimeOut() {
        return jwtTimeOut;
    }

    public void setJwtTimeOut(Long jwtTimeOut) {
        this.jwtTimeOut = jwtTimeOut;
    }
}
