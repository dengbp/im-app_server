package com.yr.net.app.moments.bo;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName momentsLikeQueryBo
 * @Description 动态点赞查询条件
 * @date 2020-12-18 13:36
 */

@Data
public class CommentsLikeQueryBo {

    /** 多个逗号分开 **/
    private final String momentsIds;

    public CommentsLikeQueryBo(String momentsIds) {
        this.momentsIds = momentsIds;
    }
}
