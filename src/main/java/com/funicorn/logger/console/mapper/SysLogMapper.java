package com.funicorn.logger.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.model.CountLogModel;
import com.funicorn.logger.console.model.CountLogReturnModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 统计
     * @param countLogModel countByRangeTimeModel
     * @return {@link List}<{@link CountLogReturnModel}>
     */
    List<CountLogReturnModel> queryCountByRangeTime(@Param("countLogModel") CountLogModel countLogModel);
}
