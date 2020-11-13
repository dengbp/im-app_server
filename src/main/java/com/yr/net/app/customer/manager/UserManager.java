package com.yr.net.app.customer.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yr.net.app.common.exception.AppException;
import com.yr.net.app.customer.bo.OnlineBo;
import com.yr.net.app.customer.dto.UserInfoResponseDto;
import com.yr.net.app.customer.entity.ShiroSession;
import com.yr.net.app.customer.mapper.ShiroSessionMapper;
import com.yr.net.app.customer.mapper.UserCoordinateMapper;
import com.yr.net.app.customer.mapper.UserInfoMapper;
import com.yr.net.app.shiro.DBSessionDao;
import com.yr.net.app.tools.SortUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author dengbp
 * @ClassName FunInfoManager
 * @Description TODO
 * @date 2020-11-12 11:39
 */
@Service
public class UserManager {

    @Autowired
    UserCoordinateMapper userCoordinateMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    private ShiroSessionMapper shiroSessionMapper;

    @Autowired
    DBSessionDao dbSessionDao;

    public IPage<UserInfoResponseDto> loadOnlineUsers(OnlineBo onlineBo)throws AppException {
        Page<ShiroSession> page = new Page<>();
        SortUtil.handlePageSort(onlineBo, page);
        LambdaQueryWrapper<ShiroSession> wrapper = new LambdaQueryWrapper<>();
        IPage<ShiroSession> sessionIPage = shiroSessionMapper.selectPage(page,wrapper);

        throw  new AppException("未完成....");
    }
}

