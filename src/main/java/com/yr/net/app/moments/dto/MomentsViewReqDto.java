package com.yr.net.app.moments.dto;

import com.yr.net.app.moments.entity.View;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description TODO
 * @date 2019-11-28 10:27
 */
@Data
public class MomentsViewReqDto implements Serializable {

    /** 发布动态的用户id */
    @NotNull
    private String publicUserId;
    /** 发布动态id */
    @NotNull
    private Long momentId;


    /** 浏览的用户id */
    private String viewUserId;

    private String likeAddr;


    public MomentsViewReqDto(@NotNull String publicUserId, @NotNull Long momentId, String viewUserId) {
        this.publicUserId = publicUserId;
        this.momentId = momentId;
        this.viewUserId = viewUserId;
    }

    public  View build(){
        View view = new View();
        view.setLikeAddr(likeAddr);
        view.setMomentId(momentId);
        view.setPublicUserId(publicUserId);
        view.setViewUserId(viewUserId);
        view.setViewTime(LocalDateTime.now());
        return view;
    }

}
