package com.yr.net.app.customer.service.impl;

import com.yr.net.app.customer.entity.UserSettings;
import com.yr.net.app.customer.mapper.UserSettingsMapper;
import com.yr.net.app.customer.service.IUserSettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, UserSettings> implements IUserSettingsService {

}
