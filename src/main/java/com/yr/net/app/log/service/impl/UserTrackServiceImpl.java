package com.yr.net.app.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.log.dto.UserTrackRespDto;
import com.yr.net.app.log.entity.UserTrack;
import com.yr.net.app.log.mapper.UserTrackMapper;
import com.yr.net.app.log.service.IUserTrackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserTrackServiceImpl extends ServiceImpl<UserTrackMapper, UserTrack> implements IUserTrackService {

    @Override
    public List<UserTrackRespDto> findByUserId(String userId, Integer operatorType)throws AppException {
        LambdaQueryWrapper<UserTrack> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTrack::getUserId, StringUtils.isBlank(userId)? AppUtil.getCurrentUserId() :userId).eq(UserTrack::getOperatorType,operatorType);
        List<UserTrackRespDto> respDtos = new ArrayList<>();
        List<UserTrack> list = this.list(queryWrapper);
        if (!list.isEmpty()){
            list.forEach(e->{
                UserTrackRespDto respDto = new UserTrackRespDto();
                BeanUtils.copyProperties(e,respDto);
                if (e.getStartTime() != null) {
                    respDto.setViewTime(DateUtil.formatFullTime(e.getStartTime()));
                }
                respDtos.add(respDto);
            });
        }
        return respDtos;
    }
}
