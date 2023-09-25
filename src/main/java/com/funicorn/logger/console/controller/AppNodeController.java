package com.funicorn.logger.console.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.boot.starter.model.Result;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.service.IAppNodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户端节点管理
 *
 * @author Aimee
 * @since 2023-03-10
 */
@RestController
@RequestMapping("/appNode")
public class AppNodeController {

    @Resource
    private IAppNodeService appNodeService;

    /**
     * 节点列表
     * @param appName appName
     * @return Result
     * */
    @GetMapping("/list")
    public Result<List<AppNode>> list(@RequestParam String appName) {
        return Result.ok(appNodeService.list(Wrappers.<AppNode>lambdaQuery().eq(AppNode::getAppName,appName).orderByAsc(AppNode::getCreatedTime)));
    }

    /**
     * 删除节点
     * @param id id
     * @return Result
     * */
    @DeleteMapping("/delete/{id}")
    public Result<?> deleteById(@PathVariable String id) {
        AppNode appNode = appNodeService.getById(id);
        if (appNode!=null) {
            if (appNode.getOnline()==1) {
                return Result.error("在线状态无法删除");
            }
            appNodeService.removeById(id);
        }
        return Result.ok();
    }
}
