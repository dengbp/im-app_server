package com.yr.net.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.base.entity.ChPlaces;
import com.yr.net.app.base.mapper.ChPlacesMapper;
import com.yr.net.app.base.service.IChPlacesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.net.app.common.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class ChPlacesServiceImpl extends ServiceImpl<ChPlacesMapper, ChPlaces> implements IChPlacesService {

    @Override
    public List<ChPlaces> findByParentId(Integer parentId) throws AppException {
        return this.list(new LambdaQueryWrapper<ChPlaces>().eq(ChPlaces::getParentId,parentId));
    }
}
