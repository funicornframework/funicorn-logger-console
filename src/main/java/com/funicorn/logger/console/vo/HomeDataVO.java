package com.funicorn.logger.console.vo;

/**
 * @author Aimee
 * @since 2023/3/13 16:16
 */
public class HomeDataVO {

    /**
     * 应用
     */
    private Integer appNum = 0;

    /**
     * 日志
     */
    private Long logNum = 0L;

    /**
     * 用户
     */
    private Long userNum = 0L;

    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

    public Long getLogNum() {
        return logNum;
    }

    public void setLogNum(Long logNum) {
        this.logNum = logNum;
    }

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "HomeDataVO{" +
                "appNum=" + appNum +
                ", logNum=" + logNum +
                ", userNum=" + userNum +
                '}';
    }
}
