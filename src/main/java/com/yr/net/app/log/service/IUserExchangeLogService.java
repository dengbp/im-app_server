package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.log.entity.UserExchangeLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IUserExchangeLogService extends IService<UserExchangeLog> {

    /**
     * Description 查询动态支付类型数据
     * @param itemId 项目id
     * @param userId 当前要查看的用户id(支付方id)
     * @param byViewUserId 被查看的用户id
     * @param type 支付项目类型
     * @throws AppException
     * @return int 是否已付款费0已付，1未付款
     * @Author dengbp
     * @Date 10:50 PM 4/1/21
     **/


    int findMomentPayByUser(Long itemId, String userId, String byViewUserId, ExchangeItem type)throws AppException;

    /**
     * Description 用户交易记录
     * @param reqDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 8:31 PM 6/2/21
     **/
    void insert(ExchangeLogReqDto reqDto)throws AppException;

}
