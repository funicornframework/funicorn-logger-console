package com.funicorn.logger.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.logger.console.entity.AppNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2023-03-10
 */
@Mapper
public interface AppNodeMapper extends BaseMapper<AppNode> {

    /**
     * 查询节点信息
     * @param appName appName
     * @param ip ip
     * @param port port
     * @return {@link AppNode}
     */
    AppNode queryAppNode(String appName,String ip, String port);

    /**
     * 恢复app_info
     * @param id id
     * */
    void updateAppNodeNotDeleted(String id);
}
