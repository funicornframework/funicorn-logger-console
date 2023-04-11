package com.funicorn.logger.console.netty;

import com.funicorn.framework.common.netty.command.CommandChannelInitializer;
import com.funicorn.framework.common.netty.core.NettyFactory;
import com.funicorn.logger.console.netty.callback.ChannelDisconnectCallback;
import com.funicorn.logger.console.netty.handler.AuthHandler;
import com.funicorn.logger.console.netty.handler.LogCollectHandler;
import com.funicorn.logger.console.netty.handler.LogHeartBeatHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aimee
 * @since 2023/3/2 11:08
 */
@Component
public class NettyServerStarter implements CommandLineRunner {

    @Value("${funicorn.logger.admin.port:17777}")
    private int port;
    @Resource
    private LogCollectHandler logCollectHandler;
    @Resource
    private LogHeartBeatHandler logHeartBeatHandler;
    @Resource
    private AuthHandler authHandler;
    @Resource
    private ChannelDisconnectCallback channelDisconnectCallback;

    @Override
    public void run(String... args) throws Exception {
        CommandChannelInitializer commandChannelInitializer = new CommandChannelInitializer(channelDisconnectCallback);
        commandChannelInitializer.addHandler(authHandler,logCollectHandler,logHeartBeatHandler);
        NettyFactory.getInstance().createServer(port,commandChannelInitializer);
    }
}
