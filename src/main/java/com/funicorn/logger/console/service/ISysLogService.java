package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.vo.RequestTopVO;

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
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> groupByOperateType();

    /**
     * 集团通过应用程序名称
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> groupByAppName();

    /**
     * 请求url top5
     *
     * @return List
     */
    List<RequestTopVO> requestUrlTop5();

    /**
     * 请求url top5
     *
     * @return List
     */
    List<RequestTopVO> requestUserTop5();
}
