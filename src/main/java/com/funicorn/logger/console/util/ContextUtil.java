package com.funicorn.logger.console.util;

import com.funicorn.logger.console.config.WebRequestFilter;
import com.funicorn.logger.console.entity.UserInfo;
import com.funicorn.logger.console.model.ContextUserInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Aimee
 * @since 2023/3/29 10:58
 */
public class ContextUtil {

    /**
     * 得到用户上下文
     *
     * @return {@link UserInfo}
     */
    public static ContextUserInfo getContextUser(){
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return (ContextUserInfo) session.getAttribute(WebRequestFilter.USER_INFO_KEY);
    }
}
