package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.entity.UserSignLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserSignLogService extends IService<UserSignLog> {

    /**
     * Description 用户登录记录查询
     * @param userId
     * @return java.util.List<com.yr.net.app.log.entity.UserSignLog>
     * @throws AppException
     * @Author dengbp
     * @Date 11:38 AM 2/21/21
     **/

    List<UserSignLog> searchByUserId(String userId)throws AppException;
}
