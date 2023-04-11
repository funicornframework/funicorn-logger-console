package com.funicorn.logger.console.vo;


import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.entity.AppNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aimee
 * @since 2023/3/10 15:16
 */
public class AppInfoVO extends AppInfo {

    /**
     * 节点num
     */
    private Integer nodeNum = 0;

    /**
     * 启用节点num
     */
    private Integer enableNodeNum = 0;

    /**
     * 健康节点num
     */
    private Integer healthNodeNum = 0;

    /**
     * 节点
     */
    private List<AppNode> nodes = new ArrayList<>();

    public List<AppNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<AppNode> nodes) {
        this.nodes = nodes;
    }

    public Integer getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(Integer nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Integer getEnableNodeNum() {
        return enableNodeNum;
    }

    public void setEnableNodeNum(Integer enableNodeNum) {
        this.enableNodeNum = enableNodeNum;
    }

    public Integer getHealthNodeNum() {
        return healthNodeNum;
    }

    public void setHealthNodeNum(Integer healthNodeNum) {
        this.healthNodeNum = healthNodeNum;
    }

    @Override
    public String toString() {
        return "AppInfoVO{" +
                "nodeNum=" + nodeNum +
                ", enableNodeNum=" + enableNodeNum +
                ", healthNodeNum=" + healthNodeNum +
                '}';
    }
}
