package com.yr.net.app.moments.dto;

import com.yr.net.app.moments.entity.CommentArea;
import lombok.Data;

/**
 * @author dengbp
 * @ClassName CommentRespDto
 * @Description TODO
 * @date 3/30/21 2:18 PM
 */
@Data
public class CommentRespDto {

    /** 当前评论id */
    private Long id;
    /** 0:对原主题评论 1:回复评论  */
    private int commentType;

    private String parentUserName;

    private String childUserName;

    private String parentUserId;

    private String childUserId;

    private String commentContent;



    public static CommentRespDto assembly(CommentArea comment,String parentUserId,String parentUserName){
        CommentRespDto respDto = new CommentRespDto();
        respDto.setCommentType(comment.getType());
        respDto.setCommentContent(comment.getCommentContent());
        respDto.setChildUserId(comment.getUserId());
        respDto.setChildUserName(comment.getUserName());
        respDto.setParentUserId(parentUserId);
        respDto.setParentUserName(parentUserName);
        respDto.setId(comment.getId());
        return respDto;
    }

}
