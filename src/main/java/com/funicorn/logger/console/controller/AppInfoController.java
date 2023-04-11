package com.funicorn.logger.console.controller;

import com.funicorn.boot.starter.model.Result;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.vo.AppInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户端信息管理
 *
 * @author Aimee
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/appInfo")
public class AppInfoController {

    @Resource
    private IAppInfoService appInfoService;

    /**
     * 客户端列表
     *
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<AppInfoVO>> list(){
        return Result.ok(appInfoService.listAppsByUsername());
    }
}
