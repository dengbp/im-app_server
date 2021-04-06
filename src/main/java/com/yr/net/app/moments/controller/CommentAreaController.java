package com.yr.net.app.moments.controller;


import com.yr.net.app.base.dto.RestResult;
import com.yr.net.app.common.annotation.ControllerEndpoint;
import com.yr.net.app.common.annotation.Log;
import com.yr.net.app.moments.dto.AddMomentAreaDto;
import com.yr.net.app.moments.entity.CommentArea;
import com.yr.net.app.moments.service.ICommentAreaService;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description 评论控制器
 * @Author dengbp
 * @Date 20:40 2020-12-17
 **/
@RestController
@RequestMapping("/moments/comment-area")
public class CommentAreaController {

    @Resource
    private ICommentAreaService commentAreaService;

    @PostMapping("/add")
    @ControllerEndpoint(operation = "追加评论", exceptionMessage = "追加评论失败")
    @ResponseBody
    @Log("追加评论接口")
    public RestResult add(@RequestBody @Valid AddMomentAreaDto addMomentDto){
        if (StringUtils.isBlank(addMomentDto.getCommentContent())){
            return RestResult.error("评论内容不能为空");
        }
        if (StringUtils.isBlank(addMomentDto.getUserId())){
            addMomentDto.setUserId(AppUtil.getCurrentUserId());
        }
        if (StringUtils.isBlank(addMomentDto.getIcon())){
            return RestResult.error("评论者头像不能为空");
        }
        if (StringUtils.isBlank(addMomentDto.getUserName())){
            return RestResult.error("评论者昵称不能为空");
        }
        if (addMomentDto.getCommentId()==null){
            return RestResult.error("主题id或评论id不能为空");
        }
        if (addMomentDto.getType()==null){
            return RestResult.error("评论类型不能为空");
        }
        addMomentDto.setState(CommentArea.NORMAL);
        commentAreaService.add(addMomentDto);
        return RestResult.ok();
    }


    @PostMapping("/delete")
    @ControllerEndpoint(operation = "删除评论", exceptionMessage = "删除评论评论失败")
    @ResponseBody
    @Log("删除评论接口")
    public RestResult delete(@RequestBody @Valid AddMomentAreaDto addMomentDto){
        if (addMomentDto.getId()==null){
            return RestResult.error("删除的评论ID不能为空");
        }
        commentAreaService.delete(addMomentDto);
        return RestResult.ok();
    }



    @PostMapping("/list")
    @ControllerEndpoint(operation = "评论查询", exceptionMessage = "评论查询失败")
    @ResponseBody
    @Log("评论查询接口")
    public RestResult list(@RequestBody @Valid AddMomentAreaDto addMomentDto){
        if (addMomentDto.getCommentId()==null){
            return RestResult.error("被评论的主题id或评论id不能为空");
        }
        return RestResult.ok().setResult(commentAreaService.list(addMomentDto));
    }

}
