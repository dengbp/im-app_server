package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.service.IUserInfoService;
import com.yr.net.app.log.dto.OperationReportDto;
import com.yr.net.app.log.dto.UserTrackRespDto;
import com.yr.net.app.log.entity.UserTrack;
import com.yr.net.app.log.mapper.UserTrackMapper;
import com.yr.net.app.log.service.IUserTrackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import com.yr.net.app.tools.ZodiacUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserTrackServiceImpl extends ServiceImpl<UserTrackMapper, UserTrack> implements IUserTrackService {
    @Autowired
   private IUserInfoService userInfoService;

    @Override
    public List<UserTrackRespDto> findByUserId(String userId, Integer operatorType)throws AppException {
        LambdaQueryWrapper<UserTrack> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTrack::getUserId, StringUtils.isBlank(userId)? AppUtil.getCurrentUserId() :userId).eq(UserTrack::getOperatorType,operatorType);
        List<UserTrackRespDto> respS = new ArrayList<>();
        List<UserTrack> list = this.list(queryWrapper);
        if (!list.isEmpty()){
            list.forEach(e->{
                UserTrackRespDto respDto = new UserTrackRespDto();
                BeanUtils.copyProperties(e,respDto);
                if (e.getStartTime() != null) {
                    respDto.setViewTime(DateUtil.formatFullTime(e.getStartTime()));
                }
                UserInfo userInfo = userInfoService.getByUserId(e.getByOperatorId());
                if (userInfo != null) {
                    respDto.setBodyHeight(userInfo.getBodyHeight().movePointRight(2).intValue());
                    respDto.setBodyWeight(userInfo.getBodyHeight());
                    respDto.setZodiac(UserInfo.getZodiac(userInfo));
                    try {
                        respDto.setAge(DateUtil.getAge(userInfo.getBirthday().toString(),DateUtil.YYYY_MM_DD_PATTERN));
                    } catch (Exception es) {
                        es.printStackTrace();
                        throw  new AppException("获取年龄异常");
                    }
                }
                respS.add(respDto);
            });
        }
        return respS;
    }

    @Override
    public void saveTrack(OperationReportDto report) throws AppException {
        UserTrack track = new UserTrack();
        track.setUserId(report.getUserId());
        track.setByOperatorId(report.getByOperatorId());
        track.setOperatorType(report.getOperatorType());
        track.setStartTime(LocalDateTime.now());
        UserInfo userInfo  = userInfoService.getByUserId(report.getByOperatorId());
        if (userInfo != null) {
            track.setByOperatorIcon(userInfo.getIcon());
            track.setByOperatorName(userInfo.getUserName());
        }
    }

    @Override
    public Integer getFans(String userId) throws AppException {
        return 0;
    }

    @Override
    public Integer getFollows(String userId) throws AppException {
        return 0;
    }
}
