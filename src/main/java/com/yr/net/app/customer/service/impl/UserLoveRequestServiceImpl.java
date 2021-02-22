package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserLoveRequestSetDto;
import com.yr.net.app.customer.entity.UserLoveRequest;
import com.yr.net.app.customer.mapper.UserLoveRequestMapper;
import com.yr.net.app.customer.service.IUserLoveRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 */
@Service
public class UserLoveRequestServiceImpl extends ServiceImpl<UserLoveRequestMapper, UserLoveRequest> implements IUserLoveRequestService {

    @Override
    public UserLoveRequest findByUserId(UserBaseInfoRequestDto requestDto) throws AppException {
        /**  如果为空代表取自己的信息，有值代表取他人的信息 */
        String userId = StringUtils.isBlank(requestDto.getUserId())? AppUtil.getCurrentUserId():requestDto.getUserId();
        LambdaQueryWrapper<UserLoveRequest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserLoveRequest::getUserId,userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void setLoveRequest(String userId, UserLoveRequestSetDto requestSetDto) throws AppException {
        if (this.count(new LambdaQueryWrapper<UserLoveRequest>().eq(UserLoveRequest::getUserId,requestSetDto.getUserId()))==0) {
            this.save(UserLoveRequest.create(requestSetDto));
            return;
        }
        LambdaUpdateWrapper<UserLoveRequest> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserLoveRequest::getUserId,userId);
        if (StringUtils.isNotBlank(requestSetDto.getAgeRange())) {
            updateWrapper.set(UserLoveRequest::getEducation,requestSetDto.getAgeRange());
        }
        if (StringUtils.isNotBlank(requestSetDto.getBloodType())) {
            updateWrapper.set(UserLoveRequest::getBloodType,requestSetDto.getBloodType());
        }
        if (StringUtils.isNotBlank(requestSetDto.getCharacterRequest())) {
            updateWrapper.set(UserLoveRequest::getCharacterRequest,requestSetDto.getCharacterRequest());
        }
        if (StringUtils.isNotBlank(requestSetDto.getEducation())) {
            updateWrapper.set(UserLoveRequest::getEducation,requestSetDto.getEducation());
        }
        if (StringUtils.isNotBlank(requestSetDto.getSalary())) {
            updateWrapper.set(UserLoveRequest::getSalary,requestSetDto.getSalary());
        }
        if (StringUtils.isNotBlank(requestSetDto.getZodiac())) {
            updateWrapper.set(UserLoveRequest::getZodiac,requestSetDto.getZodiac());
        }
        if (requestSetDto.getJobRequest() != null) {
            updateWrapper.set(UserLoveRequest::getJobRequest,requestSetDto.getJobRequest());
        }
        if (StringUtils.isNotBlank(requestSetDto.getBodyHeight())) {
            updateWrapper.set(UserLoveRequest::getBodyHeight,requestSetDto.getBodyHeight());
        }
        if (StringUtils.isNotBlank(requestSetDto.getBodyWeight())) {
            updateWrapper.set(UserLoveRequest::getBodyWeight,requestSetDto.getBodyWeight());
        }
        if (requestSetDto.getMarita() != null) {
            updateWrapper.set(UserLoveRequest::getMarita,requestSetDto.getMarita());
        }
        if (StringUtils.isNotBlank(requestSetDto.getC1())) {
            updateWrapper.set(UserLoveRequest::getC1,new BigDecimal(requestSetDto.getC1()));
        }
        if (StringUtils.isNotBlank(requestSetDto.getC2())) {
            updateWrapper.set(UserLoveRequest::getC2,new BigDecimal(requestSetDto.getC2()));
        }
        if (StringUtils.isNotBlank(requestSetDto.getC3())) {
            updateWrapper.set(UserLoveRequest::getC3,new BigDecimal(requestSetDto.getC3()));
        }
        updateWrapper.set(UserLoveRequest::getUpdatedTime, LocalDateTime.now());
        this.update(updateWrapper);
    }
}
