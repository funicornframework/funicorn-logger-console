package com.funicorn.logger.console.vo;

/**
 * @author Aimee
 * @since 2023/3/14 11:02
 */
public class RequestTopVO {

    /**
     * 应用程序名称
     */
    private String appName;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求次数
     */
    private Long requestNum = 0L;

    /**
     * 用户名
     */
    private String username;

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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Long getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(Long requestNum) {
        this.requestNum = requestNum;
    }

    @Override
    public String toString() {
        return "RequestUrlTopVO{" +
                "appName='" + appName + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestNum=" + requestNum +
                '}';
    }
}
