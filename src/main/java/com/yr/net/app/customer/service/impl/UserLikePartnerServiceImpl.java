package com.yr.net.app.customer.service.impl;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.LoveReportRequestDto;
import com.yr.net.app.customer.entity.UserLikePartner;
import com.yr.net.app.customer.mapper.UserLikePartnerMapper;
import com.yr.net.app.customer.service.IUserLikePartnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Service
public class UserLikePartnerServiceImpl extends ServiceImpl<UserLikePartnerMapper, UserLikePartner> implements IUserLikePartnerService {

    @Override
    public void add(LoveReportRequestDto reportRequestDto) throws AppException {
        UserLikePartner partner = new UserLikePartner();
        partner.setUserId(AppUtil.getCurrentUserId());
        partner.setPartnerId(reportRequestDto.getUserId());
        partner.setTag(reportRequestDto.getTag());
        partner.setCreatedTime(LocalDateTime.now());
        this.save(partner);
    }
}
