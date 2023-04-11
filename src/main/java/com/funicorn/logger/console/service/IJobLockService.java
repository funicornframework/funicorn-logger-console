package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.logger.console.entity.JobLock;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-16
 */
public interface IJobLockService extends IService<JobLock> {

    /**
     * 试着锁
     *
     * @param lockKey 锁定键
     * @return boolean
     */
    boolean tryLock(String lockKey);

    /**
     * 试着锁
     *
     * @param lockKey       锁定键
     * @param expireSeconds 到期秒
     * @return boolean
     */
    boolean tryLock(String lockKey,int expireSeconds);

    /**
     * 解锁
     *
     * @param lockKey 锁定键
     */
    void unlock(String lockKey);
}
