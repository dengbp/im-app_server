package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.pay.dto.ExchangeLogReqDto;
import com.yr.net.app.log.entity.UserExchangeLog;
import com.yr.net.app.log.mapper.UserExchangeLogMapper;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.RandomUtil;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserExchangeLogServiceImpl extends ServiceImpl<UserExchangeLogMapper, UserExchangeLog> implements IUserExchangeLogService {


    /**
     * Description 查询动态支付类型数据
     * @param momentId
     * @param userId
     * @throws AppException
     * @return int 是否已付款费0已付，1未付款
     * @Author dengbp
     * @Date 10:50 PM 4/1/21
     **/
    @Override
    public int findMomentPayByUser(Long momentId, String userId, ExchangeItem type) throws AppException {
        if (this.count(new LambdaQueryWrapper<UserExchangeLog>().eq(UserExchangeLog::getPayUserId,userId)
                .eq(UserExchangeLog::getExchangeType,UserExchangeLog.PAY_TYPE).eq(UserExchangeLog::getExchangeState,UserExchangeLog.SUCCESS)
                .eq(UserExchangeLog::getItemId,momentId).eq(UserExchangeLog::getExchangeItemType, type.getType()))
        >0){
            return  0;
        }
        return 1;
    }

    @Override
    public void insert(ExchangeLogReqDto reqDto) throws AppException {
        UserExchangeLog log = reqDto.buildEntity();
        log.setFlowCode(RandomUtil.randomStr(32));
        save(log);
    }


}
