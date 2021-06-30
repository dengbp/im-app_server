package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.entity.UserCoordinate;
import com.yr.net.app.customer.service.IUserCoordinateService;
import com.yr.net.app.moments.dto.MomentsViewReqDto;
import com.yr.net.app.moments.entity.View;
import com.yr.net.app.moments.mapper.ViewMapper;
import com.yr.net.app.moments.service.IViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@Service
public class ViewServiceImpl extends ServiceImpl<ViewMapper, View> implements IViewService {

    @Resource
    IUserCoordinateService userCoordinateService;

    @Override
    public void add(MomentsViewReqDto reqDto) throws AppException {
        View view = getOne(new LambdaQueryWrapper<View>().eq(View::getMomentId,reqDto.getMomentId()).eq(View::getViewUserId,reqDto.getViewUserId()));
        if (view != null){
            return;
        }
        UserCoordinate coordinate = userCoordinateService.findByUserId(reqDto.getViewUserId());
        if (coordinate != null){
            reqDto.setLikeAddr(coordinate.getFormattedAddress());
        }
        view = reqDto.build();
        save(view);
    }

    @Override
    public int viewCount(Long momentId) {
        return count(new LambdaQueryWrapper<View>().eq(View::getMomentId,momentId));
    }
}
