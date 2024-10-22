package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentAreaQueryBo;
import com.yr.net.app.moments.dto.AddMomentAreaDto;
import com.yr.net.app.moments.dto.CommentRespDto;
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
    void add(AddMomentAreaDto dto)throws AppException;

    /**
     * Description 根据动态id查评论
     * @param momentId 动态id
 * @param type  评论的类型 0：对主题评论；1：对评论内容评论
     * @param limit  条数
     * @throws AppException
     * @return java.util.List<com.yr.net.app.moments.entity.CommentArea>
     * @Author dengbp
     * @Date 6:51 PM 3/30/21
     **/

    List<CommentArea> getByMomentId(Long momentId,Integer type,Integer limit)throws AppException;


    /**
     * Description 删除评论
     * @param dto
     * @throws AppException
     * @return void
     * @Author dengbp
     * @Date 11:50 AM 2/24/21
     **/
    void delete(AddMomentAreaDto dto)throws AppException;

    /**
     * Description 评论列表内容
     * @param dto
     * @return java.util.List<com.yr.net.app.moments.dto.CommentRespDto>
     * @Author dengbp
     * @Date 5:33 PM 3/31/21
     **/

    List<CommentRespDto> list(AddMomentAreaDto dto)throws AppException;

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
