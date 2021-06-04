package com.yr.net.app.log.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.dto.UserSignTrackResp;
import com.yr.net.app.log.entity.UserSignLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserSignLogService extends IService<UserSignLog> {

    /**
     * Description 用户登录记录查询
     * @param userId 被查看的用户id
     * @param itemId 项目id
     * @return UserSignTrackResp
     * @throws AppException
     * @Author dengbp
     * @Date 11:38 AM 2/21/21
     **/

    UserSignTrackResp searchByUserId(Long itemId,String userId)throws AppException;

    UserSignLog searchByUserId(String userId)throws AppException;
}
