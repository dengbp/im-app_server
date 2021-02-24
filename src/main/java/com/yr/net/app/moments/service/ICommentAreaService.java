package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.dto.AddMomentDto;
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
     * Description 增加评论
     * @param dto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 11:50 AM 2/24/21
     **/
    void add(AddMomentDto dto)throws AppException;


    /**
     * Description 删除评论
     * @param dto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 11:50 AM 2/24/21
     **/
    void delete(AddMomentDto dto)throws AppException;

    /**
     * Description 评论列表内容
     * @param dto
     * @throws AppException
     * @return java.util.List<com.yr.net.app.moments.entity.CommentArea>
     * @Author dengbp
     * @Date 11:51 AM 2/24/21
     **/
    List<CommentArea> list(AddMomentDto dto)throws AppException;

    /**
     * Description 获取评论数量
     * @param queryBo
     * @param type 评论类型 0：对主题评论；1：对评论内容评论
     * @throws AppException
     * @return java.util.Map<java.lang.Long,java.lang.AtomicInteger>
     * @Author dengbp
     * @Date 13:44 2020-12-18
     **/

    Map<Long, AtomicInteger> getCommentTotal(CommentAreaQueryBo queryBo,Integer type)throws AppException;

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
