package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.AlbumRequestDto;
import com.yr.net.app.customer.dto.CoordinateRequestDto;
import com.yr.net.app.customer.dto.MultimediaResponseDto;
import com.yr.net.app.customer.dto.VideoRequestDto;
import com.yr.net.app.customer.entity.UserMultimedia;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    void saveFile(MultipartFile file, int type, int isFree, String price, CoordinateRequestDto coordinate,String showWord)throws AppException;

    /**
     * Description 视频列表
     * @param requestDto requestDto
     * @throws AppException
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.yr.net.app.customer.dto.MultimediaResponseDto>
     * @Author dengbp
     * @Date 18:28 2020-12-04
     **/
    IPage<MultimediaResponseDto> videoList(VideoRequestDto requestDto)throws AppException;


    /**
     * Description 个人相册
     * @param requestDto requestDto
     * @throws AppException
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.yr.net.app.customer.dto.MultimediaResponseDto>
     * @Author dengbp
     * @Date 18:28 2020-12-04
     **/
    IPage<MultimediaResponseDto> getAlbum(AlbumRequestDto requestDto)throws AppException;

}
