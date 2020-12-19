package com.yr.net.app.moments.bo;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName CommentMultiQueryBo
 * @Description 动态多媒体查询条件
 * @date 2020-12-18 13:23
 */

@Data
public class CommentMultiQueryBo {

    /** 多个逗号分开 **/
    private final String momentsIds;

    public CommentMultiQueryBo(String momentsIds) {
        this.momentsIds = momentsIds;
    }
}
