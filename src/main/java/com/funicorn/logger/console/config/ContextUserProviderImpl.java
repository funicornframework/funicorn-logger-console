package com.funicorn.logger.console.config;

import com.funicorn.framework.common.context.config.ContextUserProvider;
import com.funicorn.framework.common.context.core.ContextUser;
import com.funicorn.logger.console.entity.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Aimee
 * @since 2023/3/3 16:50
 */
@Component
public class ContextUserProviderImpl extends ContextUserProvider {

    @Override
    public ContextUser getContextUserDetail() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        return (UserInfo) session.getAttribute(WebRequestFilter.USER_INFO_KEY);
    }
}
