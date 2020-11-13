package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserInfoResponseDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.manager.UserManager;
import com.yr.net.app.customer.mapper.UserInfoMapper;
import com.yr.net.app.customer.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.tools.SortUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserManager userManager;

    @Override
    public IPage<UserInfoResponseDto> findOnline(OnlineRequestDto query) throws AppException {



        Page<UserInfo> page = new Page<>();
        SortUtil.handlePageSort(query, page, "CREATED_TIME", AppConstant.ORDER_DESC, false);
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        IPage<UserInfo> infoIPage = this.baseMapper.selectPage(page,wrapper);
        List<UserInfoResponseDto> userInfoResponses = new ArrayList<>();
        BeanUtils.copyProperties(infoIPage.getRecords(),userInfoResponses);
        Page<UserInfoResponseDto> result = new Page();
        result.setRecords(userInfoResponses);
        result.setSize(infoIPage.getSize());
        result.setTotal(infoIPage.getTotal());
        result.setCurrent(infoIPage.getCurrent());
        return result;
    }
}
