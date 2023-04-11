package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.framework.common.base.date.DateTimeUtil;
import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.mapper.SysLogMapper;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.ISysLogService;
import com.funicorn.logger.console.vo.AppInfoVO;
import com.funicorn.logger.console.vo.RequestTopVO;
import com.funicorn.logger.module.OperateType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Map<String, Object> groupByOperateType() {
        Map<String, Object> resultMap = new HashMap<>(8);
        for (OperateType value : OperateType.values()) {
            resultMap.put(value.name(),0);
        }
        List<AppInfoVO> result = appInfoService.listAppsByUsername();
        if (result==null || result.isEmpty()) {
            return resultMap;
        }
        List<String> apps = result.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" operation_type as operationType, count(operation_type) as count ");
        queryWrapper.groupBy("operation_type");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.in(SysLog::getAppName,apps);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()));
        logLambdaQueryWrapper.le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date()));
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> stringObjectMap : resultList) {
            resultMap.put((String) stringObjectMap.get("operationType"), stringObjectMap.get("count"));
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> groupByAppName() {
        List<AppInfoVO> result = appInfoService.listAppsByUsername();
        if (result==null || result.isEmpty()) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(8);
        for (AppInfo appInfo : result) {
            resultMap.put(appInfo.getAppName(),0);
        }
        List<String> apps = result.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" app_name as appName, count(app_name) as count ");
        queryWrapper.groupBy("app_name");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.in(SysLog::getAppName,apps);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()));
        logLambdaQueryWrapper.le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date()));
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> stringObjectMap : resultList) {
            resultMap.put((String) stringObjectMap.get("appName"), stringObjectMap.get("count"));
        }
        return resultMap;
    }

    @Override
    public List<RequestTopVO> requestUrlTop5() {
        List<AppInfoVO> resultApps = appInfoService.listAppsByUsername();
        if (resultApps==null || resultApps.isEmpty()) {
            return null;
        }
        List<String> apps = resultApps.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" app_name as appName,request_url as requestUrl,count(1) as count ");
        queryWrapper.groupBy("app_name","request_url");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.in(SysLog::getAppName,apps);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()));
        logLambdaQueryWrapper.le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date()));
        logLambdaQueryWrapper.last("ORDER BY count desc limit 5");
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        if (resultList==null || resultList.isEmpty()) {
            return null;
        }
        List<RequestTopVO> result = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : resultList) {
            RequestTopVO requestTopVO = new RequestTopVO();
            requestTopVO.setAppName((String) stringObjectMap.get("appName"));
            requestTopVO.setRequestUrl((String) stringObjectMap.get("requestUrl"));
            requestTopVO.setRequestNum((Long) stringObjectMap.get("count"));
            result.add(requestTopVO);
        }
        return result;
    }

    @Override
    public List<RequestTopVO> requestUserTop5() {
        List<AppInfoVO> resultApps = appInfoService.listAppsByUsername();
        if (resultApps==null || resultApps.isEmpty()) {
            return null;
        }
        List<String> apps = resultApps.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(" username,count(1) as count ");
        queryWrapper.groupBy("username");
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = queryWrapper.lambda();
        logLambdaQueryWrapper.in(SysLog::getAppName,apps);
        logLambdaQueryWrapper.ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()));
        logLambdaQueryWrapper.le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date()));
        logLambdaQueryWrapper.isNotNull(SysLog::getUsername);
        logLambdaQueryWrapper.last("ORDER BY count desc limit 5");
        List<Map<String, Object>> resultList = this.baseMapper.selectMaps(queryWrapper);
        if (resultList==null || resultList.isEmpty()) {
            return null;
        }
        List<RequestTopVO> result = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : resultList) {
            RequestTopVO requestTopVO = new RequestTopVO();
            requestTopVO.setUsername((String) stringObjectMap.get("username"));
            requestTopVO.setRequestNum((Long) stringObjectMap.get("count"));
            result.add(requestTopVO);
        }
        return result;
    }
}
