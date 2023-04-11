package com.funicorn.logger.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.funicorn.framework.common.datasource.model.BaseEntity;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Aimee
 * @since 2023-03-10
 */
@TableName("app_node")
public class AppNode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 客户端标识
     */
    private String appName;

    /**
     * IP
     */
    private String ip;

    /**
     * 是否在线 0 离线 1在线
     */
    private Integer online;

    /**
     * 心跳时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime heartbeatTime;

    public LocalDateTime getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(LocalDateTime heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "AppNode{" +
        "id=" + id +
        ", appName=" + appName +
        ", ip=" + ip +
        ", online=" + online +
        "}";
    }
}
