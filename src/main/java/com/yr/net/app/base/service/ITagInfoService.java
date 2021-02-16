package com.yr.net.app.base.service;

import com.yr.net.app.base.entity.TagInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.net.app.common.exception.AppException;

import java.util.List;

/**
 * @author dengbp
 */
public interface ITagInfoService extends IService<TagInfo> {

    /**
     * Description 性格类数据查询
     * @param
     * @return java.util.List<com.yr.net.app.base.entity.TagInfo>
     * @throws AppException
     * @Author dengbp
     * @Date 2:14 AM 2/16/21
     **/
    List<TagInfo> character()throws AppException;

}
