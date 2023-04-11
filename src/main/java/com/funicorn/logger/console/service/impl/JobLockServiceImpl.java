package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.logger.console.entity.JobLock;
import com.funicorn.logger.console.mapper.JobLockMapper;
import com.funicorn.logger.console.service.IJobLockService;
import com.funicorn.logger.console.util.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-16
 */
@Service
public class JobLockServiceImpl extends ServiceImpl<JobLockMapper, JobLock> implements IJobLockService {

    private static final Logger logger = LoggerFactory.getLogger(IJobLockService.class);

    private static final int DEFAULT_EXPIRE_SECONDS = 5 * 60;

    /**
     * 服务端口
     */
    @Value("${server.port}")
    private Integer port;

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;

    @Override
    public boolean tryLock(String lockKey){
        return tryLock(lockKey,DEFAULT_EXPIRE_SECONDS);
    }

    @Override
    public boolean tryLock(String lockKey, int expireSeconds){
        if (StringUtils.isEmpty(lockKey)) {
            return false;
        }
        String ip = IpUtil.getIpAddress();
        if (StringUtils.isBlank(ip)) {
            return false;
        }

        boolean lockFlag = false;
        String lockHolder = ip + ":" + port + ":" + Thread.currentThread().getId();
        //手动管理事务
        TransactionStatus transactionStatus = null;
        JobLock lock = getLock(lockKey);
        try {
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            if (lock==null) {
                lock = new JobLock();
                lock.setLockHolder(lockHolder);
                lock.setLockStatus(1);
                lock.setLockKey(lockKey);
                lock.setCreateTime(new Date());
                lock.setExpireTime(getExpireTime(lock.getCreateTime(),expireSeconds));
                save(lock);
                lockFlag = true;
            } else {
                Date expiredTime = lock.getExpireTime();
                if (expiredTime.before(new Date()) && lock.isLock()) {
                    //for update 锁住
                    JobLock forUpdateLock = this.baseMapper.lockForUpdate(lockKey);
                    if (forUpdateLock!=null) {
                        forUpdateLock.setLockHolder(lockHolder);
                        forUpdateLock.setLockStatus(1);
                        forUpdateLock.setLockKey(lockKey);
                        forUpdateLock.setCreateTime(new Date());
                        forUpdateLock.setExpireTime(getExpireTime(forUpdateLock.getCreateTime(),expireSeconds));
                        updateById(forUpdateLock);
                        lockFlag = true;
                    }
                }
            }
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            if (transactionStatus!=null) {
                dataSourceTransactionManager.rollback(transactionStatus);
            }
            lockFlag = false;
        }
        return lockFlag;
    }

    @Override
    public void unlock(String lockKey) {
        String ip = IpUtil.getIpAddress();
        if (StringUtils.isNotBlank(ip)) {
            String lockHolder = ip + ":" + port + ":" + Thread.currentThread().getId();
            JobLock jobLock = getOne(Wrappers.<JobLock>lambdaQuery().eq(JobLock::getLockKey,lockKey).eq(JobLock::getLockHolder,lockHolder));
            if (jobLock!=null) {
                removeById(jobLock.getId());
                logger.info("The lock [{}] has been released by [{}]", lockKey, lockHolder);
            }
        }
    }

    private JobLock getLock(String lockKey){
        return getOne(Wrappers.<JobLock>lambdaQuery().eq(JobLock::getLockKey,lockKey).last("limit 1"));
    }

    private Date getExpireTime(Date date,int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
