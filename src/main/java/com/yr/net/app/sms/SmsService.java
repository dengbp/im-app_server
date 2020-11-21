package com.yr.net.app.sms;


import com.yr.net.app.base.dto.RestResult;

public interface SmsService {
    RestResult.RestCode sendCode(String mobile, String code);
}
