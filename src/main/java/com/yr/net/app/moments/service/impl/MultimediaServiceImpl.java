package com.yr.net.app.moments.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.moments.bo.CommentMultiQueryBo;
import com.yr.net.app.moments.entity.Multimedia;
import com.yr.net.app.moments.mapper.MultimediaMapper;
import com.yr.net.app.moments.service.IMultimediaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class MultimediaServiceImpl extends ServiceImpl<MultimediaMapper, Multimedia> implements IMultimediaService {

    @Override
    public List<Multimedia> findByComment(CommentMultiQueryBo commentMultiQueryBo) throws AppException {
        LambdaQueryWrapper<Multimedia> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(commentMultiQueryBo.getMomentsIds())) {
            queryWrapper.in(Multimedia::getCommentId,commentMultiQueryBo.getMomentsIds());
        }
        return this.list(queryWrapper);
    }
}
