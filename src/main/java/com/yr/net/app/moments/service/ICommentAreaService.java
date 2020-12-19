package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.entity.CommentArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengbp
 */
public interface ICommentAreaService extends IService<CommentArea> {


    /**
     * Description 获取评论数量
     * @param queryBo
     * @throws AppException
     * @return java.util.Map<java.lang.Long,java.lang.AtomicInteger>
     * @Author dengbp
     * @Date 13:44 2020-12-18
     **/

    Map<Long, AtomicInteger> getCommentTotal(CommentAreaQueryBo queryBo)throws AppException;

    /**
     * Description 评论count统计
     * @param source
     * @param result
     * @return void
     * @Author dengbp
     * @Date 14:09 2020-12-18
     **/
    default void total(List<CommentArea> source, Map<Long, AtomicInteger> result){
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
