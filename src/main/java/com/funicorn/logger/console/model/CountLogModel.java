package com.funicorn.logger.console.model;

import java.util.Date;

/**
 * @author Aimee
 * @since 2023/9/22 16:21
 */
public class CountLogModel {

    /**
     * 应用模块
     * */
    private String appName;

    /**
     * 开始时间
     * */
    private Date startTime;

    /**
     * 结束时间
     * */
    private Date endTime;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
}
