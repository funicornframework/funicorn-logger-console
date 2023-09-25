package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.logger.console.constant.LoggerConstants;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.mapper.AppNodeMapper;
import com.funicorn.logger.console.service.IAppNodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppNodeServiceImpl extends ServiceImpl<AppNodeMapper, AppNode> implements IAppNodeService {

    @Override
    public void saveOrUpdateAppNode(String appName, String ip, int port) {
        AppNode appNode = baseMapper.queryAppNode(appName,ip,String.valueOf(port));
        if (appNode==null) {
            synchronized (this) {
                appNode = baseMapper.queryAppNode(appName,ip,String.valueOf(port));
                if (appNode==null) {
                    //新增节点
                    AppNode strangeAppNode = new AppNode();
                    strangeAppNode.setAppName(appName);
                    strangeAppNode.setHeartbeatTime(LocalDateTime.now());
                    strangeAppNode.setOnline(1);
                    strangeAppNode.setIp(ip);
                    strangeAppNode.setPort(port);
                    save(strangeAppNode);
                }
            }
        } else {
            if (LoggerConstants.DELETED.equals(appNode.getDeleted())) {
                baseMapper.updateAppNodeNotDeleted(appNode.getId());
            }

            LambdaUpdateWrapper<AppNode> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AppNode::getAppName,appName);
            updateWrapper.eq(AppNode::getIp,ip);
            updateWrapper.eq(AppNode::getPort,port);
            updateWrapper.set(AppNode::getHeartbeatTime,LocalDateTime.now());
            updateWrapper.set(AppNode::getOnline,1);
            baseMapper.update(null,updateWrapper);
        }
    }
}
