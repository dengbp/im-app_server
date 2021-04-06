package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentsLikeQueryBo;
import com.yr.net.app.moments.dto.MomentsLikeReqDto;
import com.yr.net.app.moments.entity.Like;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
public interface ILikeService extends IService<Like> {

    void add(MomentsLikeReqDto dto)throws AppException;

    /**
     * Description 取点赞数
     * @param queryBo
     * @param type 点赞类型 0：对主题点赞；1：对评论内容点赞
     * @throws AppException
     * @return java.util.Map<java.lang.Long,java.util.concurrent.atomic.AtomicInteger>
     * @Author dengbp
     * @Date 14:02 2020-12-18
     **/


    Map<Long, AtomicInteger> getCommentLikeTotal(CommentsLikeQueryBo queryBo,Integer type)throws AppException;

    /**
     * Description 取点赞状态
     * @param commentId
 * @param userId
     * @throws AppException
     * @return com.yr.net.app.moments.entity.Like
     * @Author dengbp
     * @Date 1:27 AM 3/29/21
     **/
    Like getByMomentAndUser(Long commentId,String userId)throws AppException;

    /**
     * Description 评论count统计
     * @param source
     * @param result
     * @return void
     * @Author dengbp
     * @Date 14:09 2020-12-18
     **/
    default void total(List<Like> source, Map<Long, AtomicInteger> result){
        if (source != null) {
            source.forEach(e->{
                if (result.containsKey(e.getCommentId())) {
                    result.get(e.getCommentId()).incrementAndGet();
                } else {
                    result.put(e.getCommentId(),new AtomicInteger(1));
                }
            });
        }
    }
}
