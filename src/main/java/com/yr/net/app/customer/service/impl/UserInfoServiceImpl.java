package com.yr.net.app.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.base.service.IZodiacInfoService;
import com.yr.net.app.common.entity.AppConstant;
import com.yr.net.app.common.entity.QueryRequestPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.configure.AppProperties;
import com.yr.net.app.customer.dto.*;
import com.yr.net.app.customer.entity.UserInfo;
import com.yr.net.app.customer.manager.UserManager;
import com.yr.net.app.customer.mapper.UserInfoMapper;
import com.yr.net.app.customer.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.pojo.Position;
import com.yr.net.app.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Autowired
    private IZodiacInfoService zodiacInfoService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserBaseInfoResponseDto> findOnline(OnlineRequestDto query) throws AppException {

        Page<UserInfo> page = new Page<>();
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        IPage<UserInfo> infoIPage = this.baseMapper.selectPage(page, wrapper);
        List<UserBaseInfoResponseDto> userInfoResponses = new ArrayList<>();
        infoIPage.getRecords().forEach(e->{
            UserBaseInfoResponseDto responseDto = new UserBaseInfoResponseDto();
            BeanUtils.copyProperties(e,responseDto);
            String birthday = e.getBirthday().toString();
            try {
                responseDto.setAge(DateUtil.getAge(birthday,DateUtil.YYYY_MM_DD_PATTERN));
            } catch (Exception e1) {
                e1.printStackTrace();
                throw new AppException(e1.getMessage());
            }
            responseDto.setZodiac(UserInfo.getZodiac(e));
            responseDto.setType(0);
            responseDto.setIsFree(0);
            responseDto.setPrice(BigDecimal.ZERO);
            responseDto.setBodyHeight(e.getBodyHeight().movePointRight(2).intValue());
            userInfoResponses.add(responseDto);
        });
        return userInfoResponses;
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
    public void updateByUserId(AddBaseInfoRequestDto baseInfo) throws AppException {
        this.update(new LambdaUpdateWrapper<UserInfo>().set(UserInfo::getUserName,baseInfo.getUserName()).
                set(UserInfo::getSex,baseInfo.getSex()).set(UserInfo::getBodyWeight,baseInfo.getBodyWeight()).
                set(UserInfo::getBodyHeight,baseInfo.getBodyHeight()).set(UserInfo::getBirthday,baseInfo.getBirthday()).
                eq(UserInfo::getUserId, baseInfo.getUserId()));
    }

    /**
     * Description 取附近的人参考：
     * @see com.yr.net.app.NearBySearchTest
     * @param userId
 * @param position
     * @throws AppException
     * @return java.util.List<com.yr.net.app.customer.dto.NearUserResponseDto>
     * @Author dengbp
     * @Date 10:47 PM 12/26/20
     **/
    @Override
    public List<NearUserResponseDto> findNear(QueryRequestPage requestPage, String userId, Position position) throws AppException {
        Page<UserInfo> page = new Page<>();
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        page.setCurrent(requestPage.getPageNum());
        page.setSize(requestPage.getPageSize());
        IPage<UserInfo> list = this.baseMapper.selectPage(page, wrapper);
        List<NearUserResponseDto> responses = new ArrayList<>();
        list.getRecords().forEach(userInfo -> {
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
        String birthday = userInfo.getBirthday().toString();
        responseDto.setAge(DateUtil.getAge(birthday,DateUtil.YYYY_MM_DD_PATTERN));
        responseDto.setDistance(Double.valueOf("1.50"));
        String year = birthday.substring(0,4);
        String moth = birthday.substring(4,6);
        String day = birthday.substring(6);
        //要初始化到用户星座表去ZodiacInfo，从星座表里查
        responseDto.setStar(ZodiacUtil.getStar(Integer.parseInt(moth),Integer.parseInt(day)));
        responseDto.setZodiac(ZodiacUtil.getZodiac(Integer.parseInt(year)));
        responseDto.setUserName(userInfo.getUserName());
        responseDto.setSex(userInfo.getSex());
        responseDto.setBodyHeight(userInfo.getBodyHeight().movePointRight(2).intValue());
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
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo,responseDto);
            responseDto.setBodyHeight(userInfo.getBodyHeight().multiply(new BigDecimal("100")).intValue());

        }
        try {
            responseDto.setAge(DateUtil.getAge(userInfo.getBirthday().toString(),DateUtil.YYYY_MM_DD_PATTERN));
        } catch (Exception e) {
            e.printStackTrace();
            throw  new AppException("获取年龄异常");
        }
        return responseDto;
    }

    @Override
    public UserInfo getRandOne() {
        return userInfoMapper.randOne();
    }
}
