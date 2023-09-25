package com.funicorn.logger.console.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aimee
 * @since 2023/9/23 9:15
 */
public class CountLogVO {

    /**
     * x时间轴
     * */
    private List<String> times;

    /**
     * 应用模块集合
     * */
    private List<String> appNames;

    /**
     * 日志统计结果
     * */
    Map<String,List<CountByRangeTimeVO>> counts = new HashMap<>();

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<String> getAppNames() {
        return appNames;
    }

    public void setAppNames(List<String> appNames) {
        this.appNames = appNames;
    }

    public Map<String, List<CountByRangeTimeVO>> getCounts() {
        return counts;
    }

    public void setCounts(Map<String, List<CountByRangeTimeVO>> counts) {
        this.counts = counts;
    }
}
