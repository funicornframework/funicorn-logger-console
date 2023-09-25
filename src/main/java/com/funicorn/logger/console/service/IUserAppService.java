package com.funicorn.logger.console.service;

import com.funicorn.logger.console.entity.UserApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户与客户端关系表 服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-29
 */
public interface IUserAppService extends IService<UserApp> {

    /**
     * 检查和绑定用户应用程序
     *
     * @param clientSecret 客户端密钥
     * @param appName  应用程序名称
     */
    void checkAndBindUserApp(String clientSecret,String appName);
}
