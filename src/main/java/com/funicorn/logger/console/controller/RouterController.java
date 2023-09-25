package com.funicorn.logger.console.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.framework.common.base.date.DateTimeUtil;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.entity.SysLog;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.ISysLogService;
import com.funicorn.logger.console.vo.AppInfoVO;
import com.funicorn.logger.console.vo.AppsVO;
import com.funicorn.logger.console.vo.HomeDataVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Aimee
 * @since 2023/3/4 9:21
 */
@Controller
public class RouterController {

    @Resource
    private IAppInfoService appInfoService;
    @Resource
    private ISysLogService sysLogService;

    @GetMapping("/")
    public String toRoot(Model model){
        return toHome(model);
    }

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String toHome(Model model){
        HomeDataVO homeDataVO = new HomeDataVO();
        List<AppInfoVO> result = appInfoService.listAppsByUsername();
        if (result!=null && !result.isEmpty()){
            homeDataVO.setAppNum(result.size());
            List<String> apps = result.stream().map(AppInfoVO::getAppName).collect(Collectors.toList());
            long logCount = sysLogService.count(Wrappers.<SysLog>lambdaQuery()
                    .in(SysLog::getAppName,apps)
                    .ge(SysLog::getOperationTime, DateTimeUtil.getDayStartTime(new Date()))
                    .le(SysLog::getOperationTime,DateTimeUtil.getDayEndTime(new Date())));
            homeDataVO.setLogNum(logCount);
            homeDataVO.setUserNum(sysLogService.countOptUserToday());
            int healthNodeNum = 0;
            int nodeNum = 0;
            for (AppInfoVO appInfoVO : result) {
                healthNodeNum += appInfoVO.getHealthNodeNum();
                nodeNum += appInfoVO.getNodeNum();
            }
            homeDataVO.setNodeDesc(healthNodeNum + " / " + nodeNum);
        }
        model.addAttribute("homeData",homeDataVO);
        return "home";
    }

    @GetMapping("/log")
    public String toLog(){
        return "log";
    }

    @GetMapping("/user")
    public String toUser(){
        return "user";
    }

    @GetMapping("/app")
    public String toApp(Model model){
        List<AppInfoVO> result = appInfoService.listAppsByUsername();
        AppsVO apps = new AppsVO();
        apps.setAppNum(result.size());
        int nodeNum = 0;
        int healthNodeNum = 0;
        for (AppInfoVO appInfoVO : result) {
            nodeNum += appInfoVO.getNodeNum();
            healthNodeNum += appInfoVO.getHealthNodeNum();
        }
        apps.setNodeNum(nodeNum);
        apps.setHealthNodeNum(healthNodeNum);
        model.addAttribute("apps",apps);
        return "app";
    }

    @GetMapping("/logout")
    public String logout(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.invalidate();
        return "login";
    }
}
