package com.yr.net.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.base.entity.TagInfo;
import com.yr.net.app.base.mapper.TagInfoMapper;
import com.yr.net.app.base.service.ITagInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class TagInfoServiceImpl extends ServiceImpl<TagInfoMapper, TagInfo> implements ITagInfoService {

    @Override
    public List<TagInfo> character() throws AppException {
        LambdaQueryWrapper<TagInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(TagInfo::getTagType,0);
        return this.list(queryWrapper);
    }
}
