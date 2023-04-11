package com.funicorn.logger.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.boot.starter.valid.Insert;
import com.funicorn.logger.console.dto.UserDTO;
import com.funicorn.logger.console.entity.UserInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Aimee
 * @since 2023-03-03
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 添加用户
     *
     * @param userDTO 用户dto
     */
    void add(UserDTO userDTO);
}
