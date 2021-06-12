package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserWithdrawalsLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.pay.dto.WithdrawReqDto;

/**
 * @author dengbp
 */
public interface IUserWithdrawalsLogService extends IService<UserWithdrawalsLog> {

    /**
     * Description 提现申请
     * @param dto
     * @throws AppException
     * @return boolean
     * @Author dengbp
     * @Date 1:45 PM 6/8/21
     **/


    boolean withdraw(WithdrawReqDto dto)throws AppException;
}
