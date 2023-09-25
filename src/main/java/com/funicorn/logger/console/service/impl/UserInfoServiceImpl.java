package com.funicorn.logger.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.logger.console.dto.UserDTO;
import com.funicorn.logger.console.entity.UserInfo;
import com.funicorn.logger.console.mapper.UserInfoMapper;
import com.funicorn.logger.console.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(UserDTO userDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(userDTO.getUserType().name());
        userInfo.setUsername(userDTO.getUsername());
        userInfo.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userInfo.setNickname(userDTO.getNickname());
        userInfo.setClientSecret(UUID.randomUUID().toString().replaceAll("-",""));
        save(userInfo);
        if (StringUtils.isBlank(userDTO.getNickname())) {
            LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserInfo::getId,userInfo.getId());
            updateWrapper.set(UserInfo::getNickname,"用户" + userInfo.getId());
            update(updateWrapper);
        }
    }
}
