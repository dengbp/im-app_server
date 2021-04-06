package com.yr.net.app.moments.dto;

import com.yr.net.app.common.entity.QueryRequestPage;
import lombok.Data;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description TODO
 * @date 2019-11-28 10:27
 */
@Data
public class UserMomentsReqDto extends QueryRequestPage {

    private String userId;

    private Long momentId;


}
