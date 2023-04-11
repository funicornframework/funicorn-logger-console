package com.funicorn.logger.console.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.boot.starter.model.Result;
import com.funicorn.logger.console.dto.SysLogQueryDTO;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.ISysLogService;
import com.funicorn.logger.console.vo.AppInfoVO;
import com.funicorn.logger.console.vo.RequestTopVO;
import com.funicorn.logger.console.vo.ResultPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户操作日志管理 前端控制器
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Resource
    private ISysLogService sysLogService;
    @Resource
    private IAppInfoService appInfoService;

    /**
     * group by操作类型
     *
     * @return Result
     */
    @ResponseBody
    @GetMapping("/groupByOperateType")
    public Result<Map<String, Object>> groupByOperateType(){
        return Result.ok(sysLogService.groupByOperateType());
    }

    /**
     * group by操作类型
     *
     * @return Result
     */
    @ResponseBody
    @GetMapping("/groupByAppName")
    public Result<Map<String, Object>> groupByAppName(){
        return Result.ok(sysLogService.groupByAppName());
    }

    /**
     * 请求url top5
     *
     * @return Result
     */
    @ResponseBody
    @GetMapping("/requestUrlTop5")
    public Result<List<RequestTopVO>> requestUrlTop5(){
        return Result.ok(sysLogService.requestUrlTop5());
    }

    /**
     * 请求用户top5
     *
     * @return Result
     */
    @ResponseBody
    @GetMapping("/requestUserTop5")
    public Result<List<RequestTopVO>> requestUserTop5(){
        return Result.ok(sysLogService.requestUserTop5());
    }

    @ResponseBody
    @GetMapping("/page")
    public ResultPage<SysLog> page(SysLogQueryDTO sysLogQueryDTO){
        List<AppInfoVO> resultApps = appInfoService.listAppsByUsername();
        if (resultApps==null || resultApps.isEmpty()) {
            ResultPage<SysLog> pageVO = new ResultPage<>();
            pageVO.setCurrent(sysLogQueryDTO.getCurrent());
            pageVO.setSize(sysLogQueryDTO.getSize());
            pageVO.setCount(0);
            pageVO.setData(null);
            return pageVO;
        }
        List<String> apps = resultApps.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
        LambdaQueryWrapper<SysLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysLog::getAppName,apps);
        if (StringUtils.isNotBlank(sysLogQueryDTO.getAppName())){
            queryWrapper.eq(SysLog::getAppName,sysLogQueryDTO.getAppName());
        }
        if (sysLogQueryDTO.getOperationType()!=null){
            queryWrapper.eq(SysLog::getOperationType,sysLogQueryDTO.getOperationType().toString());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getTenantId())){
            queryWrapper.like(SysLog::getTenantId,sysLogQueryDTO.getTenantId());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getFunctionPath())){
            queryWrapper.like(SysLog::getFunctionPath,sysLogQueryDTO.getFunctionPath());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getRequestUrl())){
            queryWrapper.like(SysLog::getRequestUrl,sysLogQueryDTO.getRequestUrl());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getRequestMethod())){
            queryWrapper.eq(SysLog::getRequestMethod,sysLogQueryDTO.getRequestMethod());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getUsername())){
            queryWrapper.like(SysLog::getUsername,sysLogQueryDTO.getUsername());
        }
        if (sysLogQueryDTO.getStartTime()!=null){
            queryWrapper.ge(SysLog::getOperationTime,sysLogQueryDTO.getStartTime());
        }
        if (sysLogQueryDTO.getEndTime()!=null){
            queryWrapper.le(SysLog::getOperationTime,sysLogQueryDTO.getEndTime());
        }
        if (StringUtils.isNotBlank(sysLogQueryDTO.getSortBy())) {
            queryWrapper.last("order by " + sysLogQueryDTO.getSortBy() + " " + sysLogQueryDTO.getSortType().toString());
        } else {
            queryWrapper.orderByDesc(SysLog::getOperationTime);
        }
        IPage<SysLog> resultPage = sysLogService.page(new Page<>(sysLogQueryDTO.getCurrent(),sysLogQueryDTO.getSize()),queryWrapper);
        ResultPage<SysLog> pageVO = new ResultPage<>();
        pageVO.setCurrent(resultPage.getCurrent());
        pageVO.setSize(resultPage.getSize());
        pageVO.setCount(resultPage.getTotal());
        pageVO.setData(resultPage.getRecords());
        return pageVO;
    }

    @GetMapping("/logInfo")
    public String logInfo(@RequestParam String id, Model model){
        SysLog sysLog = sysLogService.getById(id);
        model.addAttribute("logInfo",sysLog);
        return "logInfo";
    }
}
