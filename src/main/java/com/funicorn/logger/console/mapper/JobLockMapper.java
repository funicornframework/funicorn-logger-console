package com.funicorn.logger.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.logger.console.entity.JobLock;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2023-03-16
 */
@Mapper
public interface JobLockMapper extends BaseMapper<JobLock> {

    /**
     * 锁更新
     *
     * @param lockKey 锁定键
     * @return {@link JobLock}
     */
    JobLock lockForUpdate(String lockKey);
}
