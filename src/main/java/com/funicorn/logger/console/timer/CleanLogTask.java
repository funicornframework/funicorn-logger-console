package com.funicorn.logger.console.timer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.framework.common.base.date.DateTimeUtil;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.service.IJobLockService;
import com.funicorn.logger.console.service.ISysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Aimee
 * @since 2023/3/15 17:21
 */
@Component
public class CleanLogTask {

    private final static Logger logger = LoggerFactory.getLogger(CleanLogTask.class);

    private static final String CRON = "0 0 2 * * ?";

    private static final String LOCK_KEY = "logger:clean:log:task";

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Resource
    private ISysLogService sysLogService;
    @Resource
    private IJobLockService jobLockService;

    @Value("${funicorn.logger.admin.logretentiondays:90}")
    private Integer logretentiondays;

    @PostConstruct
    public void start(){
        if (logretentiondays==null || logretentiondays==-1) {
            return;
        }
        threadPoolTaskScheduler.schedule(() -> {
            logger.info("start execute clean log task...");
            if (jobLockService.tryLock(LOCK_KEY)) {
                sysLogService.remove(Wrappers.<SysLog>lambdaQuery().lt(SysLog::getOperationTime, DateTimeUtil.addDay(new Date(),-logretentiondays)));
                jobLockService.unlock(LOCK_KEY);
            }
        },new CronTrigger(CRON));
    }
}
