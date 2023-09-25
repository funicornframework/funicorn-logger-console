package com.funicorn.logger.console.netty.handler;

import com.funicorn.framework.common.netty.command.AbstractCommandHandler;
import com.funicorn.framework.common.netty.command.MessageExt;
import com.funicorn.framework.common.netty.core.NettyFactory;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.module.NettyCommandEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2023/3/3 11:26
 */
@Component
@ChannelHandler.Sharable
public class LogHeartBeatHandler extends AbstractCommandHandler {

    @Resource
    private IAppInfoService appInfoService;

    @Override
    public void execute(ChannelHandlerContext ctx, MessageExt messageExt) throws Exception {
        NettyFactory.getInstance().bind(ctx.channel(), (String) messageExt.getData());
        appInfoService.heartBeat(NettyFactory.getInstance().getChannel(ctx.channel().id()));
    }

    @Override
    public String getCommand() {
        return NettyCommandEnum.HEART_BEAT_COMMAND.getCode();
    }
}
