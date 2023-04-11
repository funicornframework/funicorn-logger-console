package com.funicorn.logger.console.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户与客户端关系表
 * </p>
 *
 * @author Aimee
 * @since 2023-03-29
 */
@TableName("user_app")
public class UserApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名
     */
    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "UserApp{" +
        "id=" + id +
        ", userName=" + userName +
        ", appName=" + appName +
        "}";
    }
}
