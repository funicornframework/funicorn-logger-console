package com.funicorn.logger.console.timer;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.funicorn.framework.common.base.date.DateTimeUtil;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.service.IAppNodeService;
import com.funicorn.logger.console.service.IJobLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 检查节点健康任务
 *
 * @author Aimee
 * @since 2023/3/24 10:50
 */
@Component
public class CheckNodeHealthTask {

    private final static Logger logger = LoggerFactory.getLogger(CheckNodeHealthTask.class);

    private static final String CRON = "0 0/10 * * * ?";

    private static final String LOCK_KEY = "logger:check:node:health";

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Resource
    private IAppNodeService appNodeService;
    @Resource
    private IJobLockService jobLockService;

    @PostConstruct
    public void start(){
        threadPoolTaskScheduler.schedule(() -> {
            logger.info("start execute check node health task...");
            if (jobLockService.tryLock(LOCK_KEY)) {
                Date heartTime = DateTimeUtil.addMinute(new Date(),-11);
                LambdaUpdateWrapper<AppNode> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(AppNode::getOnline,0);
                updateWrapper.eq(AppNode::getOnline,1);
                updateWrapper.le(AppNode::getHeartbeatTime,heartTime);
                appNodeService.update(updateWrapper);
                jobLockService.unlock(LOCK_KEY);
            }
        },new CronTrigger(CRON));
    }
}
