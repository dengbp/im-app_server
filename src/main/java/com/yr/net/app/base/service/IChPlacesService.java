package com.yr.net.app.base.service;

import cn.wildfirechat.common.APIPath;
import com.yr.net.app.base.entity.ChPlaces;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.common.exception.AppException;

import java.util.List;

/**
 * @author dengbp
 */
public interface IChPlacesService extends IService<ChPlaces> {

    /**
     * Description todo
     * @param parentId
     * @return java.util.List<com.yr.net.app.base.entity.ChPlaces>
     * @throws AppException
     * @Author dengbp
     * @Date 9:19 PM 2/16/21
     **/


    List<ChPlaces> findByParentId(Integer parentId)throws AppException;

}
