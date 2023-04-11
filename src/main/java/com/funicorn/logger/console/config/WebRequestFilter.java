package com.funicorn.logger.console.config;

import com.alibaba.fastjson2.JSONObject;
import com.funicorn.logger.console.util.PermitUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Aimee
 * @since 2023/3/3 16:43
 */
@Configuration
@Order(Integer.MIN_VALUE)
public class WebRequestFilter extends OncePerRequestFilter {

    public static final String USER_INFO_KEY = "user";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (PermitUtil.matchPath(request.getRequestURI())) {
            filterChain.doFilter(request,response);
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(USER_INFO_KEY)==null) {
            JSONObject responseData = new JSONObject();
            responseData.put("success",false);
            responseData.put("message","session已过期");
            responseData.put("code",-2);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(responseData.toJSONString().getBytes(StandardCharsets.UTF_8));
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
