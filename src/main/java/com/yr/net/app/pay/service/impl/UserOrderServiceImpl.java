package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.pay.entity.UserOrder;
import com.yr.net.app.pay.mapper.OrderMapper;
import com.yr.net.app.pay.service.IUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<OrderMapper, UserOrder> implements IUserOrderService {


    /**
     * 查找订单
     *
     * @param orderId
     * @return
     */
    public UserOrder findOne(Long orderId)throws AppException {
        UserOrder order = this.getById(orderId);
        return order;
    }
}
