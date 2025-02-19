package com.yr.net.app.pay.service;

import com.yr.net.app.common.exception.AppException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.entity.UserOrder;

/**
 * @author dengbp
 */
public interface IUserOrderService extends IService<UserOrder> {

    UserOrder findOne(Long orderId)throws AppException;


}
