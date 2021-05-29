package com.yr.net.app.pay.service.impl;

import com.yr.net.app.pay.entity.OrderItem;
import com.yr.net.app.pay.mapper.OrderItemMapper;
import com.yr.net.app.pay.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
