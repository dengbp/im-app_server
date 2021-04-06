package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserExchangeLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserExchangeLogService extends IService<UserExchangeLog> {

    /**
     * Description 查询动态支付类型数据
     * @param momentId
 * @param userId
     * @throws AppException
     * @return int 是否已付款费0已付，1未付款
     * @Author dengbp
     * @Date 10:50 PM 4/1/21
     **/


    int findMomentPayByUser(Long momentId,String userId)throws AppException;

}
