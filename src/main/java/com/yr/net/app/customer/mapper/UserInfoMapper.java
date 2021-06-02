package com.yr.net.app.customer.mapper;

import com.yr.net.app.customer.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author dengbp
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /** 随机取数据 */
    UserInfo randOne();

    UserInfo randOne2();

}
