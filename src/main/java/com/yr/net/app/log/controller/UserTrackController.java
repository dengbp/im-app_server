package com.yr.net.app.log.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.log.dto.OperationReportDto;
import com.yr.net.app.log.dto.OperationReqDto;
import com.yr.net.app.log.enums.OperationType;
import com.yr.net.app.log.enums.OperationType.*;
import com.yr.net.app.log.service.IUserTrackService;
import com.yr.net.app.message.service.IUserRelationService;
import com.yr.net.app.moments.dto.MomentsLikeReqDto;
import com.yr.net.app.moments.service.ILikeService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.yr.net.app.log.enums.OperationType.THEME_LIKE;

/**
 * 用户轨迹控制器
 * @author dengbp
 */
@RestController
@RequestMapping("/log/user-track")
public class UserTrackController {

    @Resource
    private IUserTrackService userTrackService;
    @Resource
    private ILikeService likeService;
    @Resource
    private IUserRelationService userRelationService;

    @PostMapping("operation")
    @ControllerEndpoint(operation = "谁看过谁相关信息接口", exceptionMessage = "谁看过谁接口失败")
    @ResponseBody
    @Log("谁看过谁相关信息接口")
    public RestResult operationBy(@RequestBody @Valid OperationReqDto reqDto){
        return RestResult.ok().setResult(userTrackService.findByUserId(reqDto.getUserId(),reqDto.getOperatorType()));
    }

    @PostMapping("save")
    @ControllerEndpoint(operation = "用户操作记录上报", exceptionMessage = "用户操作记录上报失败")
    @ResponseBody
    @Log("用户操作记录上报接口")
    public RestResult save(@RequestBody @Valid OperationReportDto reqDto){
        if (StringUtils.isBlank(reqDto.getUserId())){
            reqDto.setUserId(AppUtil.getCurrentUserId());
        }
        userTrackService.saveTrack(reqDto);
        OperationType oType = OperationType.getByCode(reqDto.getOperatorType());
        switch (oType){
            case THEME_LIKE:
                saveLike(reqDto,MomentsLikeReqDto.THEME,MomentsLikeReqDto.LIKE);
                break;
            case THEME_UNLIKE:
                saveLike(reqDto,MomentsLikeReqDto.THEME,MomentsLikeReqDto.UNLIKE);
                break;
            case COMMENT_LIKE:
                saveLike(reqDto,MomentsLikeReqDto.COMMENT,MomentsLikeReqDto.LIKE);
                break;
            case COMMENT_UNLIKE:
                saveLike(reqDto,MomentsLikeReqDto.COMMENT,MomentsLikeReqDto.UNLIKE);
                break;
            case FOLLOW:
                userRelationService.follow(reqDto.transformTo(reqDto));
                break;
            case UN_FOLLOW:
                userRelationService.follow(reqDto.transformTo(reqDto));
                break;
            default:
                break;
        }
        return RestResult.ok();
    }

    private void saveLike(OperationReportDto reqDto,Integer operationType,Integer state){
        MomentsLikeReqDto like = new MomentsLikeReqDto();
        like.setMomentId(reqDto.getMomentId());
        like.setPublicUserId(reqDto.getByOperatorId());
        like.setState(state);
        like.setType(operationType);
        likeService.add(like);
    }

}
