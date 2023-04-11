package com.funicorn.logger.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Aimee
 * @since 2023-03-16
 */
@TableName("job_lock")
public class JobLock implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 任务名称
     */
    private String lockKey;

    /**
     * 锁持有者
     */
    private String lockHolder;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 锁状态 0未锁 1已锁
     */
    private Integer lockStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    public boolean isLock(){
        return this.lockStatus == 1;
    }

    public String getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(String lockHolder) {
        this.lockHolder = lockHolder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "JobLock{" +
        "id=" + id +
        ", lockKey=" + lockKey +
        ", expireTime=" + expireTime +
        ", lockStatus=" + lockStatus +
        ", createTime=" + createTime +
        "}";
    }
}
