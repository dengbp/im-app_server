package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.dto.UserSignTrackResp;
import com.yr.net.app.log.entity.UserSignLog;
import com.yr.net.app.log.mapper.UserSignLogMapper;
import com.yr.net.app.log.service.IUserExchangeLogService;
import com.yr.net.app.log.service.IUserSignLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.pay.controller.enums.ExchangeItem;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserSignLogServiceImpl extends ServiceImpl<UserSignLogMapper, UserSignLog> implements IUserSignLogService {

    @Resource
    private IUserExchangeLogService userExchangeLogService;

    @Override
    public UserSignTrackResp searchByUserId(Long itemId,String userId) throws AppException {
        UserSignTrackResp resp = new UserSignTrackResp();
        List<UserSignLog> logs =  this.list(new LambdaQueryWrapper<UserSignLog>().eq(UserSignLog::getUserId,userId).last("limit 0 , 50"));
        resp.setSignLogs(logs);
        resp.setPurview(0);
        /** 非自己看自己 */
        if(!StringUtils.equals(userId,AppUtil.getCurrentUserId())){
            resp.setPurview(userExchangeLogService.findMomentPayByUser(itemId, AppUtil.getCurrentUserId(),userId, ExchangeItem.track));
        }
        return resp;
    }

    @Override
    public UserSignLog searchByUserId(String userId) throws AppException {
        List<UserSignLog> logs =  this.list(new LambdaQueryWrapper<UserSignLog>().eq(UserSignLog::getUserId,userId).last("limit 0 , 50"));
        if (!logs.isEmpty()){
            return logs.get(0);
        }
        return null;
    }
}
