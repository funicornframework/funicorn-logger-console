package com.funicorn.logger.console.vo;

/**
 * @author Aimee
 * @since 2023/9/22 15:54
 */
public class CountByRangeTimeVO {

    /**
     * 时间轴
     * */
    private String time;

    /**
     * 数量
     * */
    private Long sum;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
