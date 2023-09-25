package com.funicorn.logger.console.model;

/**
 * @author Aimee
 * @since 2023/9/22 16:24
 */
public class CountLogReturnModel {

    /**
     * 总数
     * */
    private Long count;

    /**
     * 应用模块
     * */
    private String appName;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
