package com.yr.net.app.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.pay.entity.OrderSeq;

/**
 * @author dengbp
 */
public interface IOrderSeqService extends IService<OrderSeq> {

    /**
     * Description todo
     * @param
     * @return long
     * @Author dengbp
     * @Date 14:55 2020-05-26
     **/
    long getSeq();

}
