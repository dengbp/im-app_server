package com.yr.net.app.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yr.net.app.pay.entity.OrderSeq;

/**
 * @author dengbp
 */
public interface OrderSeqMapper extends BaseMapper<OrderSeq> {

    /**
     * Description todo
     * @param seq
     * @return long
     * @Author dengbp
     * @Date 14:54 2020-05-26
     **/

    long insertSeq(OrderSeq seq);
}
