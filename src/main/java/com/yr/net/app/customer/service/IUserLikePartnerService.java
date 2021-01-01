package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.LoveReportRequestDto;
import com.yr.net.app.customer.entity.UserLikePartner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IUserLikePartnerService extends IService<UserLikePartner> {

    /**
     * Description 用户点击喜欢或不喜欢行为上报
     * @param reportRequestDto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 1:49 AM 12/27/20
     **/
    void add(LoveReportRequestDto reportRequestDto)throws AppException;
}
