package com.funicorn.logger.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户操作日志管理
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 中文描述
     */
    private String content;

    /**
     * 操作类型  update/delete/insert/query
     */
    private String operationType;

    /**
     * 用户操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operationTime;

    /**
     * 操作用户账号
     */
    private String username;

    /**
     * 客户端标识
     */
    private String appName;

    /**
     * 请求源ip地址
     */
    private String remoteAddr;

    /**
     * 后台函数路径
     */
    private String functionPath;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型 post/get/patch/delete
     */
    private String requestMethod;

    /**
     * 耗时 单位ms
     */
    private Long costTime;

    /**
     * 租户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "SysLog{" +
        "id=" + id +
        ", content=" + content +
        ", operationType=" + operationType +
        ", operationTime=" + operationTime +
        ", username=" + username +
        ", appName=" + appName +
        ", remoteAddr=" + remoteAddr +
        ", functionPath=" + functionPath +
        ", requestUrl=" + requestUrl +
        ", requestParam=" + requestParam +
        ", requestMethod=" + requestMethod +
        ", costTime=" + costTime +
        ", tenantId=" + tenantId +
        "}";
    }
}
