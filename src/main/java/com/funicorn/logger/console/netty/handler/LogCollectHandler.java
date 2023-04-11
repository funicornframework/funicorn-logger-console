package com.funicorn.logger.console.netty.handler;

import com.funicorn.framework.common.netty.command.AbstractCommandHandler;
import com.funicorn.framework.common.netty.command.MessageExt;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.service.ISysLogService;
import com.funicorn.logger.module.NettyCommandEnum;
import com.funicorn.logger.module.SysLogDTO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Aimee
 * @since 2023/3/2 11:04
 */
@Component
@ChannelHandler.Sharable
public class LogCollectHandler extends AbstractCommandHandler {

    @Resource
    private ISysLogService sysLogService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void execute(ChannelHandlerContext ctx, MessageExt messageExt) {
        SysLogDTO sysLogDTO = (SysLogDTO) messageExt.getData();
        threadPoolTaskExecutor.execute(() -> {
            SysLog sysLog = new SysLog();
            sysLog.setContent(sysLogDTO.getContent());
            sysLog.setOperationType(sysLogDTO.getOperationType());
            sysLog.setOperationTime(LocalDateTime.ofEpochSecond(sysLogDTO.getOperationTime()/1000, 0, ZoneOffset.ofHours(8)));
            sysLog.setUsername(sysLogDTO.getUsername());
            sysLog.setAppName(sysLogDTO.getAppName());
            sysLog.setRemoteAddr(sysLogDTO.getRemoteAddr());
            sysLog.setFunctionPath(sysLogDTO.getFunction());
            sysLog.setRequestUrl(sysLogDTO.getRequestUrl());
            sysLog.setRequestParam(sysLogDTO.getRequestParam());
            sysLog.setRequestMethod(sysLogDTO.getRequestMethod());
            sysLog.setCostTime(sysLogDTO.getCostTime());
            sysLog.setTenantId(sysLogDTO.getTenantId());
            sysLogService.save(sysLog);
        });
    }

    @Override
    public String getCommand() {
        return NettyCommandEnum.FUNICORN_LOG_COMMAND.getCode();
    }
}
