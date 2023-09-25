package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.logger.console.entity.UserApp;
import com.funicorn.logger.console.entity.UserInfo;
import com.funicorn.logger.console.mapper.UserAppMapper;
import com.funicorn.logger.console.mapper.UserInfoMapper;
import com.funicorn.logger.console.service.IUserAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户与客户端关系表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-29
 */
@Service
public class UserAppServiceImpl extends ServiceImpl<UserAppMapper, UserApp> implements IUserAppService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void checkAndBindUserApp(String clientSecret, String appName) {
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getClientSecret,clientSecret));
        if (userInfo!=null) {
            long count = count(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserName,userInfo.getUsername()).eq(UserApp::getAppName,appName));
            if (count>0) {
                return;
            }

            UserApp userApp = new UserApp();
            userApp.setUserName(userInfo.getUsername());
            userApp.setAppName(appName);
            save(userApp);
        }
    }
}
