package com.funicorn.logger.console.constant;

/**
 * @author Aimee
 * @since 2023/9/25 9:08
 */
public enum OperateTypeEnum {

    /**
     * 新增
     * */
    INSERT("新增"),

    /**
     * 新增
     * */
    UPDATE("修改"),

    /**
     * 新增
     * */
    DELETE("删除"),

    /**
     * 新增
     * */
    SELECT("查询"),

    /**
     * 登录
     * */
    LOGIN("登录"),

    /**
     * 登出
     * */
    LOGOUT("登出"),

    /**
     * 其他
     * */
    NONE("其他"),

    ;

    private String desc;

    OperateTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
