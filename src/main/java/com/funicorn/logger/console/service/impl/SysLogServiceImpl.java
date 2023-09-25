package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.framework.common.base.date.DateTimeUtil;
import com.funicorn.logger.console.constant.OperateTypeEnum;
import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.mapper.SysLogMapper;
import com.funicorn.logger.console.model.CountLogModel;
import com.funicorn.logger.console.model.CountLogReturnModel;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.ISysLogService;
import com.funicorn.logger.console.vo.AppInfoVO;
import com.funicorn.logger.console.vo.CountByRangeTimeVO;
import com.funicorn.logger.console.vo.CountLogVO;
import com.funicorn.logger.console.vo.RequestTopVO;
import com.funicorn.logger.module.OperateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户操作日志管理 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Resource
    private IAppInfoService appInfoService;

    @Override
    public long countOptUserToday() {
        List<AppInfoVO> result = appInfoService.listAppsByUsername();
        if (result==null || result.isEmpty()) {
            return 0;
        }
        List<String> apps = result.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" count(distinct username) as userCount");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.isNotNull(SysLog::getUsername);
        logLambdaQueryWrapper.in(SysLog::getAppName,apps);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()));
        logLambdaQueryWrapper.le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date()));
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        if (resultList==null || resultList.isEmpty()) {
            return 0;
        }

        Map<String, Object> countMap = resultList.get(0);
        if (countMap.containsKey("userCount")) {
            return (long) countMap.get("userCount");
        }
        return 0;
    }

    @Override
    public Map<String, Object> groupByOperateType(Date startTime, Date endTime, String appName) {
        Map<String, Object> resultMap = new HashMap<>(8);
        for (OperateTypeEnum value : OperateTypeEnum.values()) {
            resultMap.put(value.getDesc(),0);
        }
        List<String> appNames = new ArrayList<>();
        if (StringUtils.hasText(appName)) {
            appNames.add(appName);
        } else {
            List<AppInfoVO> appInfos = appInfoService.listAppsByUsername();
            appNames = appInfos.stream().map(AppInfo::getAppName).collect(Collectors.toList());
        }
        if (appNames.isEmpty()) {
            return resultMap;
        }
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" operation_type as operationType, count(operation_type) as count ");
        queryWrapper.groupBy("operation_type");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.in(SysLog::getAppName,appNames);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, startTime);
        logLambdaQueryWrapper.le(SysLog::getOperationTime, endTime);
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> stringObjectMap : resultList) {
            resultMap.put(OperateTypeEnum.valueOf((String) stringObjectMap.get("operationType")).getDesc(), stringObjectMap.get("count"));
        }
        return resultMap;
    }

    @Override
    public CountLogVO countByRangeTime(Date startTime, Date endTime, String appName) {
        CountLogVO countLogVO = new CountLogVO();
        List<String> times = new ArrayList<>();
        List<String> appNames = new ArrayList<>();
        Map<String,List<CountByRangeTimeVO>> resultMap = new HashMap<>(12);
        if (StringUtils.hasText(appName)) {
            resultMap.put(appName,new ArrayList<>());
            appNames.add(appName);
        } else {
            List<AppInfoVO> appInfos = appInfoService.listAppsByUsername();
            for (AppInfo appInfo : appInfos) {
                resultMap.put(appInfo.getAppName(),new ArrayList<>());
            }
            appNames = appInfos.stream().map(AppInfo::getAppName).collect(Collectors.toList());
        }
        long day = DateTimeUtil.calDifferDay(startTime,endTime);
        if (day<=1) {
            // 按照小时统计
            Date startHour = DateTimeUtil.getHourStartTime(startTime);
            for (int i = 0; i < 24; i++) {
                Date endHour = DateTimeUtil.getHourEndTime(startHour);
                CountLogModel countLogModel = new CountLogModel();
                countLogModel.setAppName(appName);
                countLogModel.setStartTime(startHour);
                countLogModel.setEndTime(endHour);
                List<CountLogReturnModel> list = baseMapper.queryCountByRangeTime(countLogModel);
                for (CountLogReturnModel countLogReturnModel : list) {
                    CountByRangeTimeVO countByRangeTimeVO = new CountByRangeTimeVO();
                    countByRangeTimeVO.setSum(countLogReturnModel.getCount());
                    countByRangeTimeVO.setTime(DateTimeUtil.dateToStr(startHour,"HH:mm"));
                    resultMap.get(countLogReturnModel.getAppName()).add(countByRangeTimeVO);
                }
                for (Map.Entry<String,List<CountByRangeTimeVO>> entry : resultMap.entrySet()) {
                    if (!list.stream().map(CountLogReturnModel::getAppName).collect(Collectors.toList()).contains(entry.getKey())) {
                        CountByRangeTimeVO countByRangeTimeVO = new CountByRangeTimeVO();
                        countByRangeTimeVO.setSum(0L);
                        countByRangeTimeVO.setTime(DateTimeUtil.dateToStr(startHour,"HH:mm"));
                        entry.getValue().add(countByRangeTimeVO);
                    }
                }
                times.add(DateTimeUtil.dateToStr(startHour,"HH:mm"));
                startHour = DateTimeUtil.addHour(startHour, 1);
            }
        } else {
            // 按照天统计
            Date startHour = DateTimeUtil.getDayStartTime(startTime);
            for (int i = 0; i < day+1; i++) {
                Date endHour = DateTimeUtil.getDayEndTime(startHour);
                CountLogModel countLogModel = new CountLogModel();
                countLogModel.setAppName(appName);
                countLogModel.setStartTime(startHour);
                countLogModel.setEndTime(endHour);
                List<CountLogReturnModel> list = baseMapper.queryCountByRangeTime(countLogModel);
                for (CountLogReturnModel countLogReturnModel : list) {
                    CountByRangeTimeVO countByRangeTimeVO = new CountByRangeTimeVO();
                    countByRangeTimeVO.setSum(countLogReturnModel.getCount());
                    countByRangeTimeVO.setTime(DateTimeUtil.dateToStr(startHour,"yyyy-MM-dd"));
                    resultMap.get(countLogReturnModel.getAppName()).add(countByRangeTimeVO);
                }
                for (Map.Entry<String,List<CountByRangeTimeVO>> entry : resultMap.entrySet()) {
                    if (!list.stream().map(CountLogReturnModel::getAppName).collect(Collectors.toList()).contains(entry.getKey())) {
                        CountByRangeTimeVO countByRangeTimeVO = new CountByRangeTimeVO();
                        countByRangeTimeVO.setSum(0L);
                        countByRangeTimeVO.setTime(DateTimeUtil.dateToStr(startHour,"yyyy-MM-dd"));
                        entry.getValue().add(countByRangeTimeVO);
                    }
                }
                times.add(DateTimeUtil.dateToStr(startHour,"yyyy-MM-dd"));
                startHour = DateTimeUtil.addDay(startHour, 1);
            }
        }

        //X时间轴
        countLogVO.setTimes(times);
        // 应用模块集合
        countLogVO.setAppNames(appNames);
        //统计结果
        countLogVO.setCounts(resultMap);
        return countLogVO;
    }
}
