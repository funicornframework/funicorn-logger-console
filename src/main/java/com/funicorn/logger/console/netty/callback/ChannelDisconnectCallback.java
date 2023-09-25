package com.funicorn.logger.console.netty.callback;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.funicorn.framework.common.netty.core.ChannelEx;
import com.funicorn.framework.common.netty.core.DisconnectCallback;
import com.funicorn.framework.common.netty.core.NettyFactory;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.service.IAppNodeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2023/3/16 10:26
 */
@Component
public class ChannelDisconnectCallback implements DisconnectCallback {

    @Resource
    private IAppNodeService appNodeService;

    @Override
    public void call(ChannelEx channelEx) {
        LambdaUpdateWrapper<AppNode> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(AppNode::getOnline,0);
        updateWrapper.eq(AppNode::getOnline,1);
        updateWrapper.eq(AppNode::getAppName,channelEx.getClientId());
        updateWrapper.eq(AppNode::getIp,channelEx.getTargetAddr().split(":")[0]);
        if (channelEx.getTargetAddr().split(":").length>1) {
            updateWrapper.eq(AppNode::getPort,channelEx.getTargetAddr().split(":")[1]);
        } else {
            updateWrapper.eq(AppNode::getPort,80);
        }
        appNodeService.update(updateWrapper);
    }
}
