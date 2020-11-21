package com.yr.net.app.transmitter.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.transmitter.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 * @ClassName TransmitService
 * @Description TODO
 * @date 2020-11-22 00:59
 */

public interface ITransmitService {

    /**
     * Description 消息发送
     * @param message message
     * @throws AppException AppException
     * @return void
     * @Author dengbp
     * @Date 01:02 2020-11-22
     **/
    int transmit(MessageDto message)throws AppException;

}
