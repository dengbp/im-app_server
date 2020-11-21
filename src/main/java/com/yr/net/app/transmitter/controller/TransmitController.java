package com.yr.net.app.transmitter.controller;

import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.transmitter.dto.MessageDto;
import com.yr.net.app.transmitter.service.ITransmitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 * @ClassName TransmitController
 * @Description TODO
 * @date 2020-11-22 01:06
 */
@RestController
@RequestMapping("/transmitter/")
public class TransmitController {

    @Resource
    ITransmitService  transmitService;

    @PostMapping("transmit")
    @ControllerEndpoint(operation = "给用户发送消息", exceptionMessage = "给用户发送消息失败")
    @ResponseBody
    @Log("给用户发送消息")
    public RestResult vicinity(@Valid MessageDto messageDto)throws AppException {
        return RestResult.ok().setResult(transmitService.transmit(messageDto));
    }
}
