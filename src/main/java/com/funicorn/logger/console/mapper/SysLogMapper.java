package com.funicorn.logger.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.logger.console.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户操作日志管理 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
