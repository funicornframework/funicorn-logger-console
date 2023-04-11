package com.funicorn.logger.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funicorn.framework.common.datasource.model.BaseEntity;

/**
 * <p>
 * 客户端信息表
 * </p>
 *
 * @author Aimee
 * @since 2023-03-07
 */
@TableName("app_info")
public class AppInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 客户端标识
     */
    private String appName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
        "id=" + id +
        ", appName=" + appName +
        "}";
    }
}
