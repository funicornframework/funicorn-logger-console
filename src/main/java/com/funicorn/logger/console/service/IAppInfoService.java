package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.framework.common.netty.core.ChannelEx;
import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.vo.AppInfoVO;

import java.util.List;

/**
 * <p>
 * 客户端信息表 服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-07
 */
public interface IAppInfoService extends IService<AppInfo> {

    /**
     * 心脏跳动
     *
     * @param channelEx 通道前
     */
    void heartBeat(ChannelEx channelEx);

    /**
     * 列出应用程序用户名
     * 列表
     *
     * @return  List<AppInfoVO>
     */
    List<AppInfoVO> listAppsByUsername();
}
