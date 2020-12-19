package com.yr.net.app.moments.service;

import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.entity.Multimedia;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author dengbp
 */
public interface IMultimediaService extends IService<Multimedia> {


    /**
     * Description todo
     * @param commentMultiQueryBo
     * @throws AppException
     * @return java.util.List<com.yr.net.app.moments.entity.Multimedia>
     * @Author dengbp
     * @Date 13:28 2020-12-18
     **/

    public List<Multimedia> findByComment(CommentMultiQueryBo commentMultiQueryBo)throws AppException;

}
