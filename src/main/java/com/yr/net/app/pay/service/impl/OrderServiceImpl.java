package com.yr.net.app.pay.service.impl;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.entity.UserOrder;
import com.yr.net.app.pay.mapper.OrderMapper;
import com.yr.net.app.pay.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author dengbp
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, UserOrder> implements IOrderService {

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
