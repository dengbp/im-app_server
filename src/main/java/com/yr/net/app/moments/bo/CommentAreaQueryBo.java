package com.yr.net.app.moments.bo;

import lombok.Data;

import java.util.Set;

/**
 * @author dengbp
 * @ClassName CommentAreaQueryBo
 * @Description 动态评论查询条件
 * @date 2020-12-18 13:35
 */
@Data
public class CommentAreaQueryBo {

    /** 多个逗号分开 **/
    private final Set<Long> momentsIds;

    public CommentAreaQueryBo(Set<Long> momentsIds) {
        this.momentsIds = momentsIds;
    }
}
