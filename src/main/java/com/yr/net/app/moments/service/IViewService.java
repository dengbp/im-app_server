package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.dto.MomentsViewReqDto;
import com.yr.net.app.moments.entity.View;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dengbp
 */
public interface IViewService extends IService<View> {

    void add(MomentsViewReqDto reqDto)throws AppException;

    int viewCount(Long momentId);

}
