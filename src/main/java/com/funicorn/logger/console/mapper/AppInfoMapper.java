package com.funicorn.logger.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.logger.console.entity.AppInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户端信息表 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2023-03-07
 */
@Mapper
public interface AppInfoMapper extends BaseMapper<AppInfo> {

    /**
     * 查询应用
     * @param appName appName
     * @return AppInfo
     * */
    AppInfo queryAppInfoByClientId(String appName);

    /**
     * 恢复app_info
     * @param id id
     * */
    void updateAppInfoNotDeleted(String id);
}
