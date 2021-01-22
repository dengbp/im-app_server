package com.yr.net.app.customer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.dto.*;
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
     * Description 用户多媒体
     * @param file
     * @throws AppException
     * @return MultipartFileRespDto
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    MultipartFileRespDto saveFile(MultipartFile file)throws AppException;


    /**
     * Description 用户多媒体信息
 * @param id	 多媒体id
     * @param using 用处:0个人资料里的相册(或视频),1个人动态
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

    void updateMulInfo(Long id,Integer using, int type, int isFree, String price, CoordinateRequestDto coordinate,String showWord)throws AppException;

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
    List<MultimediaResponseDto> getAlbum(AlbumRequestDto requestDto)throws AppException;

    /**
     * Description 根据多媒体删除用户媒体
     * @param ids	多媒体id
     * @return void
     * @throws AppException
     * @Author dengbp
     * @Date 10:15 PM 1/22/21
     **/
    void albumDel(List<Long> ids)throws AppException;


    /**
     * Description 获取用户相片数量
     * @param userId
     * @throws AppException
     * @return java.lang.Integer
     * @Author dengbp
     * @Date 12:55 AM 1/17/21
     **/

    Integer getAlbumCount(String userId)throws AppException;

}
