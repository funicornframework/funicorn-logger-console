package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.logger.console.entity.AppNode;
import com.funicorn.logger.console.mapper.AppNodeMapper;
import com.funicorn.logger.console.service.IAppNodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
