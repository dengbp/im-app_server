package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.customer.dto.NearUserResponseDto;
import com.yr.net.app.customer.dto.OnlineRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoRequestDto;
import com.yr.net.app.customer.dto.UserBaseInfoResponseDto;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.manager.UserManager;
import com.yr.net.app.customer.mapper.UserInfoMapper;
import com.yr.net.app.customer.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.pojo.Position;
import com.yr.net.app.tools.AppUtil;
import com.yr.net.app.tools.DateUtil;
import com.yr.net.app.tools.FileUtil;
import com.yr.net.app.tools.SortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserManager userManager;
    @Autowired
    private AppProperties appProperties;

    @Override
    public IPage<UserBaseInfoResponseDto> findOnline(OnlineRequestDto query) throws AppException {

        Page<UserInfo> page = new Page<>();
        SortUtil.handlePageSort(query, page, "CREATED_TIME", AppConstant.ORDER_DESC, false);
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        IPage<UserInfo> infoIPage = this.baseMapper.selectPage(page, wrapper);
        List<UserBaseInfoResponseDto> userInfoResponses = new ArrayList<>();
        BeanUtils.copyProperties(infoIPage.getRecords(), userInfoResponses);
        Page<UserBaseInfoResponseDto> result = new Page();
        result.setRecords(userInfoResponses);
        result.setSize(infoIPage.getSize());
        result.setTotal(infoIPage.getTotal());
        result.setCurrent(infoIPage.getCurrent());
        return result;
    }

    @Override
    public UserInfo getByUserId(String userId) throws AppException {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUserId, userId);
        List<UserInfo> userInfos = this.list(wrapper);
        if (!userInfos.isEmpty()) {
            return userInfos.get(0);
        }
        return null;
    }

    @Override
    public List<NearUserResponseDto> findNear(String userId, Position position) throws AppException {
        List<UserInfo> list = this.list();
        List<NearUserResponseDto> responses = new ArrayList<>();
        list.forEach(userInfo -> {
            try {
                responses.add(this.assemblyResponse(userInfo));
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException(e.getMessage());
            }
        });
        return responses;
    }

    private NearUserResponseDto assemblyResponse(final UserInfo userInfo) throws Exception {
        NearUserResponseDto responseDto = new NearUserResponseDto();
        responseDto.setUserId(userInfo.getUserId());
        responseDto.setIcon(userInfo.getIcon());
        responseDto.setAge(DateUtil.getAge(userInfo.getBirthday().toString(),DateUtil.YYYY_MM_DD_PATTERN));
        responseDto.setBodyHeight(userInfo.getBodyHeight().intValue());
        responseDto.setDistance(Double.valueOf("1.5"));
        return responseDto;
    }

    @Override
    public String updateIcon(MultipartFile file) throws AppException {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper();
        String url = appProperties.getMultimedia_url()+"/"+ FileUtil.getFilePath(appProperties,file.getOriginalFilename());
        updateWrapper.set(UserInfo::getIcon,url).eq(UserInfo::getUserId,AppUtil.getCurrentUserId());
        this.update(updateWrapper);
        return url;
    }

    @Override
    public UserBaseInfoResponseDto getUserInfo(UserBaseInfoRequestDto requestDto) throws AppException {
        String userId = StringUtils.isBlank(requestDto.getUserId())?AppUtil.getCurrentUserId():requestDto.getUserId();
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserId,userId);
        UserInfo userInfo = this.getOne(queryWrapper);
        UserBaseInfoResponseDto responseDto = new UserBaseInfoResponseDto();
        BeanUtils.copyProperties(userInfo,responseDto);
        return responseDto;
    }
}
