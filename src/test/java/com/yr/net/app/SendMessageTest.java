package com.yr.net.app;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.Conversation;
import cn.wildfirechat.pojos.MessagePayload;
import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import cn.wildfirechat.sdk.utilities.AdminHttpUtils;

/**
 * @author dengbp
 * @ClassName SendmessageTest
 * @Description TODO
 * @date 2020-11-22 00:30
 */
public class SendMessageTest {

    public static void main(String[] args) {
        System.currentTimeMillis();

        AdminHttpUtils.init("http://121.37.181.23:18080", "123456");
        sendTextMessage("admin","agazazMM", "sb,这是我从后台发给你的。不是真人");

        //  AdminHttpUtils.httpJsonPost()
    }


    private static void sendTextMessage(String fromUser, String toUser, String text) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(text);


        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage(fromUser, conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                System.out.println("send message success");
            } else {
                System.out.println("send message error:"+ (resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("send message error ="+e.getLocalizedMessage());
        }

    }
}
