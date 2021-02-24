package com.yr.net.app.moments.entity;

import com.yr.net.app.customer.entity.UserMultimedia;
import com.yr.net.app.tools.AppUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName UserMomentsSub
 * @Description 重新构建表与实体生成的时候不会丢失
 * @date 1/17/21 1:46 AM
 */
public class UserMomentsSub extends UserMoments{

    public static UserMoments buildUserMoment(UserMultimedia multimedia, int isFree, String price, String showWord){
        UserMoments userMoment = new UserMoments();
        userMoment.setMultimediaId(multimedia.getId());
        userMoment.setUrl(userMoment.getUrl());
        userMoment.setUserId(AppUtil.getCurrentUserId());
        userMoment.setPublicTime(LocalDateTime.now());
        userMoment.setPublicAddr(multimedia.getAddr());
        userMoment.setIsFree(isFree);
        userMoment.setPreviewUrl(multimedia.getPreviewUrl());
        if (StringUtils.isNotBlank(price)){
            userMoment.setPrice(new BigDecimal(price));
        }
        if (StringUtils.isNotBlank(showWord)){
            userMoment.setShowWord(showWord);
        }
        return userMoment;
    }
}
