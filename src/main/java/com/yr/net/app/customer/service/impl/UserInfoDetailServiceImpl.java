package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserDetailSetRequestDto;
import com.yr.net.app.customer.entity.UserInfoDetail;
import com.yr.net.app.customer.mapper.UserInfoDetailMapper;
import com.yr.net.app.customer.service.IUserInfoDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserInfoDetailServiceImpl extends ServiceImpl<UserInfoDetailMapper, UserInfoDetail> implements IUserInfoDetailService {

    @Override
    public UserInfoDetail findUserDetail(UserBaseInfoRequestDto requestDto) throws AppException {
        /**  如果为空代表取自己的信息，有值代表取他人的信息 */
        String userId = StringUtils.isBlank(requestDto.getUserId())? AppUtil.getCurrentUserId():requestDto.getUserId();
        LambdaQueryWrapper<UserInfoDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfoDetail::getUserId,userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public void updateMotto(String userId, String motto) throws AppException {
        LambdaUpdateWrapper<UserInfoDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserInfoDetail::getMotto,motto).eq(UserInfoDetail::getUserId,userId);
        this.update(updateWrapper);
    }

    @Override
    public void updateDetail(String userId,UserDetailSetRequestDto detailSetRequestDto) throws AppException {
        LambdaUpdateWrapper<UserInfoDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfoDetail::getUserId,userId);

        if (StringUtils.isNotBlank(detailSetRequestDto.getCharmPart())) {
            updateWrapper.set(UserInfoDetail::getCharmPart,detailSetRequestDto.getCharmPart());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getDisposition())) {
            updateWrapper.set(UserInfoDetail::getDisposition,detailSetRequestDto.getDisposition());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getEducation())) {
            updateWrapper.set(UserInfoDetail::getEducation,detailSetRequestDto.getEducation());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getHousing())) {
            updateWrapper.set(UserInfoDetail::getHousing,detailSetRequestDto.getHousing());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getJob())) {
            updateWrapper.set(UserInfoDetail::getJob,detailSetRequestDto.getJob());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getLoveType())) {
            updateWrapper.set(UserInfoDetail::getLoveType,detailSetRequestDto.getLoveType());
        }
        if (detailSetRequestDto.getMarita() != null) {
            updateWrapper.set(UserInfoDetail::getMarita,detailSetRequestDto.getMarita());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getNativeLand())) {
            updateWrapper.set(UserInfoDetail::getNativeLand,detailSetRequestDto.getNativeLand());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getSalary())) {
            updateWrapper.set(UserInfoDetail::getSalary,detailSetRequestDto.getSalary());
        }
        if (detailSetRequestDto.getCarPurchase() != null) {
            updateWrapper.set(UserInfoDetail::getCarPurchase,detailSetRequestDto.getCarPurchase());
        }
        if (detailSetRequestDto.getDistanceLove() != null) {
            updateWrapper.set(UserInfoDetail::getDistanceLove,detailSetRequestDto.getDistanceLove());
        }
        if (detailSetRequestDto.getHousePurchase() != null) {
            updateWrapper.set(UserInfoDetail::getHousePurchase,detailSetRequestDto.getHousePurchase());
        }
        if (detailSetRequestDto.getLiveWithParents() != null) {
            updateWrapper.set(UserInfoDetail::getLiveWithParents,detailSetRequestDto.getLiveWithParents());

        }
        if (detailSetRequestDto.getPremaritalSex() != null) {
            updateWrapper.set(UserInfoDetail::getPremaritalSex,detailSetRequestDto.getPremaritalSex());
        }
        if (detailSetRequestDto.getGiveBirth() != null) {
            updateWrapper.set(UserInfoDetail::getGiveBirth,detailSetRequestDto.getGiveBirth());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC1())) {
            updateWrapper.set(UserInfoDetail::getC1,detailSetRequestDto.getC1());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC2())) {
            updateWrapper.set(UserInfoDetail::getC2,detailSetRequestDto.getC2());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC3())) {
            updateWrapper.set(UserInfoDetail::getC3,detailSetRequestDto.getC3());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC4())) {
            updateWrapper.set(UserInfoDetail::getC4,detailSetRequestDto.getC4());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC5())) {
            updateWrapper.set(UserInfoDetail::getC5,detailSetRequestDto.getC5());
        }
        if (StringUtils.isNotBlank(detailSetRequestDto.getC6())) {
            updateWrapper.set(UserInfoDetail::getC6,detailSetRequestDto.getC6());
        }
        this.update(updateWrapper);
    }
}
