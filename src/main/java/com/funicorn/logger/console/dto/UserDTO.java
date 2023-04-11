package com.funicorn.logger.console.dto;

import com.funicorn.boot.starter.valid.Delete;
import com.funicorn.boot.starter.valid.Insert;
import com.funicorn.boot.starter.valid.Update;
import com.funicorn.logger.console.constant.UserType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Aimee
 * @since 2023/3/8 14:26
 */
public class UserDTO {

    /**
     * 用户id
     */
    @NotBlank(message = "用户ID必填",groups = {Update.class, Delete.class})
    private String userId;

    /**
     * 昵称
     */
    @NotBlank(message = "用户昵称必填",groups = {Update.class, Insert.class})
    private String nickname;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名必填",groups = {Insert.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码必填",groups = {Insert.class,Update.class})
    private String password;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型必填",groups = {Insert.class})
    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
