package com.yr.net.app.moments.bo;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName CommentAreaQueryBo
 * @Description 动态评论查询条件
 * @date 2020-12-18 13:35
 */
@Data
public class CommentAreaQueryBo {

    /** 多个逗号分开 **/
    private final String momentsIds;

    public CommentAreaQueryBo(String momentsIds) {
        this.momentsIds = momentsIds;
    }
}
