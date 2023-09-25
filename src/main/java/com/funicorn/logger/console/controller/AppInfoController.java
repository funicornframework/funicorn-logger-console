package com.funicorn.logger.console.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.boot.starter.model.Result;
import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.IAppNodeService;
import com.funicorn.logger.console.vo.AppInfoVO;
import com.funicorn.logger.console.vo.AppsVO;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private IAppNodeService appNodeService;

    /**
     * 客户端列表
     *
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<AppInfoVO>> list(){
        return Result.ok(appInfoService.listAppsByUsername());
    }

    /**
     * 应用模块信息
     * @return Result
     */
    @GetMapping("/apps")
    public Result<AppsVO> apps() {
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
        return Result.ok(apps);
    }

    /**
     * 删除应用模块
     * @param id id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable String id) {
        AppInfo appInfo = appInfoService.getById(id);
        if (appInfo!=null) {
            long count = appNodeService.count(Wrappers.<AppNode>lambdaQuery().eq(AppNode::getAppName,appInfo.getAppName()).eq(AppNode::getOnline,1));
            if (count>0) {
                return Result.error("存在健康节点，无法删除");
            } else {
                appInfoService.removeById(id);
            }
        }
        return Result.ok();
    }
}
