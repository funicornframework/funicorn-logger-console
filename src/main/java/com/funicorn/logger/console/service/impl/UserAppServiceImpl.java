package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.logger.console.entity.UserApp;
import com.funicorn.logger.console.mapper.UserAppMapper;
import com.funicorn.logger.console.service.IUserAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public void checkAndBindUserApp(String username, String appName) {
        long count = count(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserName,username).eq(UserApp::getAppName,appName));
        if (count>0) {
            return;
        }

        UserApp userApp = new UserApp();
        userApp.setUserName(username);
        userApp.setAppName(appName);
        save(userApp);
    }
}
