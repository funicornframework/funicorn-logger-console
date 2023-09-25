package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.logger.console.dto.CountByRangeTimeDTO;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.vo.CountByRangeTimeVO;
import com.funicorn.logger.console.vo.CountLogVO;
import com.funicorn.logger.console.vo.RequestTopVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户操作日志管理 服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 今天数选择用户
     *
     * @return int
     */
    long countOptUserToday();

    /**
     * group by操作类型
     * @param startTime startTime
     * @param endTime endTime
     * @param appName appName
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> groupByOperateType(Date startTime, Date endTime, String appName);

    /**
     * 按照事件统计日志数量
     * @param startTime startTime
     * @param endTime endTime
     * @param appName appName
     * @return CountLogVO
     */
    CountLogVO countByRangeTime(Date startTime, Date endTime, String appName);
}
