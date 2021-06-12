package com.yr.net.app.pay.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.dto.ExchangeLogRespDto;
import com.yr.net.app.pay.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserAccountService extends IService<UserAccount> {

    List<UserAccount> ranking()throws AppException;

    /**
     * Description 余额查询
     * @param
     * @return com.yr.net.app.pay.entity.UserAccount
     * @Author dengbp
     * @Date 9:24 PM 6/7/21
     **/

    UserAccount getBalance()throws AppException;

    /**
     * Description 用户帐单查询
     * @param
     * @return java.util.List<com.yr.net.app.pay.dto.ExchangeLogRespDto>
     * @throws AppException
     * @Author dengbp
     * @Date 10:22 AM 6/11/21
     **/
    List<ExchangeLogRespDto> transacLog()throws AppException;


    void updateByUserId(String userId,Integer totalFee)throws AppException;

    boolean pay(ExchangeLogReqDto reqDto)throws AppException;

}
