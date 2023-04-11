package com.funicorn.logger.console.vo;

/**
 * @author Aimee
 * @since 2023/3/10 15:46
 */
public class AppsVO {

    /**
     * 应用num
     */
    private Integer appNum = 0;

    /**
     * 节点num
     */
    private Integer nodeNum = 0;
    /*
     * 健康节点num
     */
    private Integer healthNodeNum = 0;

    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getHealthNodeNum() {
        return healthNodeNum;
    }

    public void setHealthNodeNum(Integer healthNodeNum) {
        this.healthNodeNum = healthNodeNum;
    }
}
