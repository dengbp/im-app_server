package com.yr.net.app.pojo;

import lombok.Data;

@Data
public class LoginResponse {
    private String userId;
    private String token;
    private boolean register;
    private String userName;
    private String portrait;
    /** 0是新用户，1不是 */
    private int isNewUser;

    public static int NEW_USER = 0;

    public static int NOT_NEW_USER = 1;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
