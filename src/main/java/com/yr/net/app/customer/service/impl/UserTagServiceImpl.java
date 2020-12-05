package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.UserTagRequestDto;
import com.yr.net.app.customer.entity.UserTag;
import com.yr.net.app.customer.mapper.UserTagMapper;
import com.yr.net.app.customer.service.IUserTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements IUserTagService {

    @Override
    public List<UserTag> findByUserId(String userId) throws AppException {
        /**  如果为空代表取自己的信息，有值代表取他人的信息 */
        userId = StringUtils.isBlank(userId)? AppUtil.getCurrentUserId():userId;
        LambdaQueryWrapper<UserTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserTag::getUserId,userId).eq(UserTag::getTagType,1);
        return this.list(queryWrapper);
    }

    @Override
    public void setUserTag(String userId, UserTagRequestDto requestDto) throws AppException {

        LambdaUpdateWrapper<UserTag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserTag::getUserId,userId).eq(UserTag::getTagId,requestDto.getTagId());
        updateWrapper.set(UserTag::getTagId,requestDto.getTagId()).set(UserTag::getState,requestDto.getState())
                .set(UserTag::getTagContent,requestDto.getTagContent()).set(UserTag::getTagType,requestDto.getTagType());
    }
}
