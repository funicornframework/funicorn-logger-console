package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.framework.common.base.json.JsonUtil;
import com.funicorn.framework.common.netty.core.ChannelEx;
import com.funicorn.logger.console.constant.LoggerConstants;
import com.funicorn.logger.console.constant.UserType;
import com.funicorn.logger.console.entity.AppInfo;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.entity.UserApp;
import com.funicorn.logger.console.mapper.AppInfoMapper;
import com.funicorn.logger.console.service.IAppInfoService;
import com.funicorn.logger.console.service.IAppNodeService;
import com.funicorn.logger.console.service.IUserAppService;
import com.funicorn.logger.console.util.ContextUtil;
import com.funicorn.logger.console.vo.AppInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户端信息表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements IAppInfoService {

    @Resource
    private IAppNodeService appNodeService;
    @Resource
    private IUserAppService userAppService;

    @Override
    public void heartBeat(ChannelEx channelEx) {
        AppInfo appInfo = baseMapper.queryAppInfoByClientId(channelEx.getClientId());
        if (appInfo==null) {
            synchronized (this) {
                appInfo = baseMapper.queryAppInfoByClientId(channelEx.getClientId());
                if (appInfo==null) {
                    //新增客户端
                    AppInfo strangeAppInfo = new AppInfo();
                    strangeAppInfo.setAppName(channelEx.getClientId());
                    save(strangeAppInfo);
                }
            }
        } else {
            if (LoggerConstants.DELETED.equals(appInfo.getDeleted())) {
                baseMapper.updateAppInfoNotDeleted(appInfo.getId());
            }
        }
        int port = 80;
        if (channelEx.getTargetAddr().split(":").length>1) {
            port = Integer.parseInt(channelEx.getTargetAddr().split(":")[1]);
        }
        appNodeService.saveOrUpdateAppNode(channelEx.getClientId(),channelEx.getTargetAddr().split(":")[0],port);
        userAppService.checkAndBindUserApp(channelEx.getAuthToken(),channelEx.getClientId());
    }

    @Override
    public List<AppInfoVO> listAppsByUsername() {
        List<AppInfo> appInfos;
        if (UserType.Admin==UserType.valueOf(ContextUtil.getContextUser().getUserType())) {
            appInfos = list();
        } else {
            List<UserApp> userApps = userAppService.list(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserName, ContextUtil.getContextUser().getUsername()));
            if (userApps==null || userApps.isEmpty()) {
                appInfos = null;
            } else {
                List<String> apps = userApps.stream().map(UserApp::getAppName).collect(Collectors.toList());
                appInfos = list(Wrappers.<AppInfo>lambdaQuery().in(AppInfo::getAppName,apps));
            }
        }
        if (appInfos==null || appInfos.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> apps = appInfos.stream().map(AppInfo::getAppName).collect(Collectors.toList());
        List<AppInfoVO> appInfoVOList = new ArrayList<>();
        List<AppNode> appNodes = appNodeService.list(Wrappers.<AppNode>lambdaQuery().in(AppNode::getAppName,apps));
        Map<String, List<AppNode>> nodeMap = appNodes.stream().collect(Collectors.groupingBy(AppNode::getAppName));
        for (AppInfo appInfo : appInfos) {
            AppInfoVO appInfoVO = JsonUtil.object2Object(appInfo,AppInfoVO.class);
            if (nodeMap.containsKey(appInfo.getAppName())) {
                appInfoVO.setNodes(nodeMap.get(appInfo.getAppName()));
                appInfoVO.setNodeNum(appInfoVO.getNodes().size());
                appInfoVO.setHealthNodeNum((int) appInfoVO.getNodes().stream().filter(node -> 1 == node.getOnline()).count());
            }
            appInfoVOList.add(appInfoVO);
        }
        return appInfoVOList;
    }
}
