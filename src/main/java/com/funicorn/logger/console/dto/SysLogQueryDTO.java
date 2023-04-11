package com.funicorn.logger.console.dto;

import com.funicorn.logger.module.OperateType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Aimee
 * @since 2023/3/7 9:23
 */
public class SysLogQueryDTO extends PageDTO {

    /**
     * 客户端标识
     */
    private String appName;

    /**
     * 操作类型
     */
    private OperateType operationType;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 函数路径
     */
    private String functionPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求url
     */
    private String requestUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public OperateType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperateType operationType) {
        this.operationType = operationType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return "SysLogQueryDTO{" +
                "appName='" + appName + '\'' +
                ", operateType=" + operationType +
                ", tenantId='" + tenantId + '\'' +
                ", username='" + username + '\'' +
                ", functionPath='" + functionPath + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
