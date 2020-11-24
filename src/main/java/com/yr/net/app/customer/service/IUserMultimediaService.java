package com.yr.net.app.customer.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author dengbp
 */
public interface IUserMultimediaService extends IService<UserMultimedia> {

    /**
     * Description todo
     * @param file
 * @param type	 多媒体类型 0：图片；1：视频
 * @param isFree	是否收费 0:免费:1收费
 * @param price
 * @param coordinate
     * @param showWord 我的秀言
     * @return void
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/


    void saveFile(MultipartFile file, int type, int isFree, String price, CoordinateRequestDto coordinate,String showWord)throws AppException;

}
