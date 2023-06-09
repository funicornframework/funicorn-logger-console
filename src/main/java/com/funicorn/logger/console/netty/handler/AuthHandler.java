package com.funicorn.logger.console.netty.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.framework.common.netty.command.AbstractCommandHandler;
import com.funicorn.framework.common.netty.command.MessageExt;
import com.funicorn.framework.common.netty.core.NettyFactory;
import com.funicorn.logger.console.entity.UserInfo;
import com.funicorn.logger.console.service.IUserInfoService;
import com.funicorn.logger.module.AuthModel;
import com.funicorn.logger.module.AuthResponse;
import com.funicorn.logger.module.NettyCommandEnum;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Aimee
 * @since 2023/3/21 9:46
 */
@Component
@ChannelHandler.Sharable
public class AuthHandler extends AbstractCommandHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void execute(ChannelHandlerContext ctx, MessageExt messageExt) throws Exception {
        if (!(messageExt.getData() instanceof AuthModel)) {
            authFailed(ctx);
            return;
        }
        AuthModel authModel = (AuthModel) messageExt.getData();
        if (authModel==null || StringUtils.isBlank(authModel.getUsername()) || StringUtils.isBlank(authModel.getPassword())) {
            authFailed(ctx);
            return;
        }
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername,authModel.getUsername()));
        if (userInfo==null) {
            authFailed(ctx);
            return;
        }
        if(!passwordEncoder.matches(authModel.getPassword(),userInfo.getPassword())) {
            authFailed(ctx);
            return;
        }

        // 通过后，移除掉this
        ctx.pipeline().remove(AuthHandler.class);
        NettyFactory.getInstance().getChannel(ctx.channel().id()).setAuthToken(((AuthModel) messageExt.getData()).getUsername());
        authSuccess(ctx);
    }

    @Override
    public String getCommand() {
        return null;
    }

    private void authSuccess(ChannelHandlerContext ctx){
        MessageExt messageExt = new MessageExt();
        messageExt.setCommand(NettyCommandEnum.AUTH_COMMAND.getCode());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAuthPass(true);
        authResponse.setAuthToken(NettyFactory.getInstance().getChannel(ctx.channel().id()).getAuthToken());
        messageExt.setData(authResponse);
        ctx.channel().writeAndFlush(messageExt);
    }

    private void authFailed(ChannelHandlerContext ctx){
        MessageExt messageExt = new MessageExt();
        messageExt.setCommand(NettyCommandEnum.AUTH_COMMAND.getCode());
        messageExt.setData(new AuthResponse());
        ctx.channel().writeAndFlush(messageExt);
        logger.warn("[{}] auth failed, the connection will be closed after 10 seconds.",NettyFactory.getInstance().getChannel(ctx.channel().id()).getTargetAddr());
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                ctx.channel().close();
            }
        },10, TimeUnit.SECONDS);
    }
}
