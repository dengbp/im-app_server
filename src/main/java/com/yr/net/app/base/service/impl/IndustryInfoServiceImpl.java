package com.yr.net.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.base.entity.IndustryInfo;
import com.yr.net.app.base.mapper.IndustryInfoMapper;
import com.yr.net.app.base.service.IIndustryInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class IndustryInfoServiceImpl extends ServiceImpl<IndustryInfoMapper, IndustryInfo> implements IIndustryInfoService {

    @Override
    public List<IndustryInfo> getJobInfo(Long parentId) throws AppException {
        return this.list(new LambdaQueryWrapper<IndustryInfo>().eq(IndustryInfo::getIndustryParentId,parentId));
    }
}
