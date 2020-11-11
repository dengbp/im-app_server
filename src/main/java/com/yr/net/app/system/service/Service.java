package com.yr.net.app.system.service;


import com.yr.net.app.RestResult;
import com.yr.net.app.pojo.CancelSessionRequest;
import com.yr.net.app.pojo.ConfirmSessionRequest;
import com.yr.net.app.pojo.CreateSessionRequest;
import com.yr.net.app.pojo.GroupAnnouncementPojo;
import cn.wildfirechat.pojos.InputCreateDevice;
import org.springframework.web.multipart.MultipartFile;

public interface Service {
    RestResult sendCode(String mobile);
    RestResult login(String mobile, String code, String clientId, int platform);


    RestResult createPcSession(CreateSessionRequest request);
    RestResult loginWithSession(String token);

    RestResult scanPc(String token);
    RestResult confirmPc(ConfirmSessionRequest request);
    RestResult cancelPc(CancelSessionRequest request);

    RestResult changeName(String newName);


    RestResult saveUserLogs(String userId, MultipartFile file);

    RestResult addDevice(InputCreateDevice createDevice);
    RestResult getDeviceList();
    RestResult delDevice(InputCreateDevice createDevice);
}
