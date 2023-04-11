package com.funicorn.logger.console.dto;


import com.funicorn.logger.console.model.SortType;

/**
 * @author Aimee
 * @since 2023/3/7 9:21
 */
public class PageDTO {

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 每页数量
     */
    private long size = 10;

    /**
     * 排序
     */
    private String sortBy;

    /**
     * 排序方式
     */
    private SortType sortType = SortType.ASC;

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "current=" + current +
                ", size=" + size +
                ", sortBy='" + sortBy + '\'' +
                ", sortType=" + sortType +
                '}';
    }
}
