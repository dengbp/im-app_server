package com.yr.net.app.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.pay.entity.OrderSeq;
import com.yr.net.app.pay.mapper.OrderSeqMapper;
import com.yr.net.app.pay.service.IOrderSeqService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author dengbp
 */
@Service
public class OrderSeqServiceImpl extends ServiceImpl<OrderSeqMapper, OrderSeq> implements IOrderSeqService {

    @Resource
    OrderSeqMapper dmpTaskSeqMapper;
    @Override
    public long getSeq() {
        OrderSeq seq = new OrderSeq();
        seq.setCreateTime(LocalDate.now());
        seq.setSeq(0L);
        dmpTaskSeqMapper.insertSeq(seq);
        return seq.getId();
    }
}
