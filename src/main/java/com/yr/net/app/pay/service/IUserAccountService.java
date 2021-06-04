package com.yr.net.app.pay.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserAccountService extends IService<UserAccount> {

    List<UserAccount> ranking()throws AppException;

    UserAccount getBalance()throws AppException;

    void updateByUserId(String userId,Integer totalFee)throws AppException;

    boolean pay(ExchangeLogReqDto reqDto)throws AppException;

}
