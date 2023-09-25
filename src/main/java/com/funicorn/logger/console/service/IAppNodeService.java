package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.logger.console.entity.AppNode;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-10
 */
public interface IAppNodeService extends IService<AppNode> {

    /**
     * 新增或保存节点
     * @param appName appName
     * @param ip ip
     * @param port port
     */
    void saveOrUpdateAppNode(String appName,String ip, int port);
}
