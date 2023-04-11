package com.funicorn.logger.console.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author Aimee
 * @since 2023/3/3 16:29
 */
public class LoginDTO {

    @NotBlank(message = "用户名必填")
    private String username;

    @NotBlank(message = "密码必填")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
