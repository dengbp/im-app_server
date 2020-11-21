package com.yr.net.app.transmitter.service.impl;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.Conversation;
import cn.wildfirechat.pojos.MessagePayload;
import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.transmitter.dto.MessageDto;
import com.yr.net.app.transmitter.service.ITransmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 * @ClassName TransmitServiceImpl
 * @Description TODO
 * @date 2020-11-22 01:00
 */
@Slf4j
@Service
public class TransmitServiceImpl implements ITransmitService {


    @Override
    public int transmit(MessageDto message) throws AppException {
        Conversation conversation = new Conversation();
        conversation.setTarget(message.getToUser());
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(message.getMessage());
        /** unknown */
        int result = -1;
        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(message.getFromUser(), conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                log.info("send message success");
            } else {
                log.error("send message error:{}",resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
            return resultSendMessage != null ? resultSendMessage.getCode():result;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("send message error:{}",e.getLocalizedMessage());
            return result;
        }

    }

}
