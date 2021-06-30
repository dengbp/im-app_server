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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
        queryWrapper.eq(UserTrack::getByOperatorId, StringUtils.isBlank(userId)? AppUtil.getCurrentUserId() :userId).eq(UserTrack::getOperatorType,operatorType).orderByDesc(UserTrack::getStartTime);
        List<UserTrackRespDto> respS = new ArrayList<>();
        List<UserTrack> list = this.list(queryWrapper);
        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserTrack :: getUserId))), ArrayList::new));
        if (!list.isEmpty()){
            list.forEach(e->{
                UserTrackRespDto respDto = conversion(e);
                if (respDto != null) {
                    respS.add(respDto);
                }
            });
        }
        return respS;
    }

    private UserTrackRespDto conversion(UserTrack origin)throws AppException{
        UserInfo userInfo = userInfoService.getByUserId(origin.getUserId());
        UserTrackRespDto target = null;
        if (userInfo != null) {
            target = new UserTrackRespDto();
            target.setBodyHeight(userInfo.getBodyHeight()==null? BigDecimal.ZERO.intValue():userInfo.getBodyHeight().movePointRight(2).intValue());
            target.setBodyWeight(userInfo.getBodyWeight());
            target.setZodiac(UserInfo.getZodiac(userInfo));
            target.setOperatorIcon(userInfo.getIcon());
            target.setOperatorType(origin.getOperatorType());
            target.setOperatorId(origin.getUserId());
            target.setOperatorName(origin.getUserName());
            if (origin.getStartTime() != null) {
                target.setViewTime(DateUtil.formatFullTime(origin.getStartTime()));
            }
            try {
                target.setAge(DateUtil.getAge(userInfo.getBirthday().toString(),DateUtil.YYYY_MM_DD_PATTERN));
            } catch (Exception es) {
                es.printStackTrace();
                throw  new AppException("获取年龄异常");
            }
        }
        return target;
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
        userInfo  = userInfoService.getByUserId(report.getUserId());
        if (userInfo != null){
            track.setUserIcon(userInfo.getIcon());
        }
        this.save(track);
    }
}
