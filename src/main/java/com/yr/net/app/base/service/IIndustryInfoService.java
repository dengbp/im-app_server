package com.yr.net.app.base.service;

import com.yr.net.app.base.entity.IndustryInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.common.exception.AppException;

import java.util.List;

/**
 * Description 职业信息服务类
 * @Author dengbp
 * @Date 11:28 PM 2/16/21
 **/
public interface IIndustryInfoService extends IService<IndustryInfo> {

    /**
     * Description 职业信息(只取级数为2的即可满足)
     * @param parentId
     * @throws AppException
     * @return java.util.List<com.yr.net.app.base.entity.IndustryInfo>
     * @Author dengbp
     * @Date 11:29 PM 2/16/21
     **/
    List<IndustryInfo> getJobInfo( Long parentId)throws AppException;

}
