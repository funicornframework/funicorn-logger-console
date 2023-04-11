package com.funicorn.logger.console.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.boot.starter.model.Result;
import com.funicorn.boot.starter.valid.Insert;
import com.funicorn.logger.console.config.WebRequestFilter;
import com.funicorn.logger.console.constant.UserType;
import com.funicorn.logger.console.dto.LoginDTO;
import com.funicorn.logger.console.dto.UserDTO;
import com.funicorn.logger.console.entity.UserInfo;
import com.funicorn.logger.console.service.IUserInfoService;
import com.funicorn.logger.console.util.ContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 *
 * 用户管理
 *
 * @author Aimee
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     *
     * @param loginDTO 登录dto
     * @return Result
     */
    @PostMapping("/login")
    public Result<UserInfo> login(@Validated LoginDTO loginDTO){
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername,loginDTO.getUsername()));
        if (userInfo==null) {
            return Result.error(-1,"用户或密码不正确");
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(),userInfo.getPassword())) {
            return Result.error(-1,"用户或密码不正确");
        }
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.setAttribute(WebRequestFilter.USER_INFO_KEY, userInfo);
        session.setMaxInactiveInterval(1800);
        return Result.ok(userInfo);
    }

    /**
     * 添加用户
     *
     * @param userDTO 用户dto
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) UserDTO userDTO){
        if (UserType.Admin!=UserType.valueOf(ContextUtil.getContextUser().getUserType())) {
            return Result.error("非管理员用户，无操作权限");
        }
        userInfoService.add(userDTO);
        return Result.ok();
    }

    /**
     * 编辑昵称
     *
     * @param userDTO 用户dto
     * @return Result
     */
    @PostMapping("/editNickname")
    public Result<?> editNickname(@RequestBody UserDTO userDTO){
        if (UserType.Admin!=UserType.valueOf(ContextUtil.getContextUser().getUserType())
                && !userDTO.getUserId().equals(ContextUtil.getContextUser().getId())) {
            return Result.error("非管理员用户，无操作权限");
        }
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,userDTO.getUserId());
        updateWrapper.set(UserInfo::getNickname,userDTO.getNickname());
        userInfoService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 修改密码
     *
     * @param userDTO 用户dto
     * @return Result
     */
    @PostMapping("/editPwd")
    public Result<?> editPwd(@RequestBody UserDTO userDTO){
        if (UserType.Admin!=UserType.valueOf(ContextUtil.getContextUser().getUserType())
                && !userDTO.getUserId().equals(ContextUtil.getContextUser().getId())) {
            return Result.error("非管理员用户，无操作权限");
        }
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,userDTO.getUserId());
        updateWrapper.set(UserInfo::getPassword,passwordEncoder.encode(userDTO.getPassword()));
        userInfoService.update(updateWrapper);
        return Result.ok();
    }

    /**
     * 用户列表
     *
     * @param username 用户名
     * @param nickname 昵称
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<UserInfo>> list(@RequestParam(required = false) String username,@RequestParam(required = false) String nickname){
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(UserInfo::getUsername, ContextUtil.getContextUser().getUsername());
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(UserInfo::getUsername,username);
        }
        if (StringUtils.isNotBlank(nickname)) {
            queryWrapper.like(UserInfo::getNickname,nickname);
        }
        List<UserInfo> userInfos = userInfoService.list(queryWrapper);
        userInfos.forEach(userInfo -> userInfo.setPassword("********************************"));
        return Result.ok(userInfos);
    }

    /**
     * 删除用户
     *
     * @param id id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable String id){
        if (UserType.Admin!=UserType.valueOf(ContextUtil.getContextUser().getUserType())) {
            return Result.error("非管理员用户，无操作权限");
        }
        userInfoService.removeById(id);
        return Result.ok();
    }
}
