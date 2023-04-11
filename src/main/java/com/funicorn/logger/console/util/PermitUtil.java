package com.funicorn.logger.console.util;

import com.google.common.collect.Lists;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * @author Aimee
 * @since 2020/7/6 14:14
 */

public class PermitUtil {

    /**
     * 静态文件白名单
     * */
    private static final List<String> ENDPOINTS = Lists.newArrayList(
            "/favicon.ico", "/v2/api-docs/**","/v3/api-docs/**","/**/layui/**", "/doc/index.html","/doc.html", "/swagger-resources/**","/**/swagger-ui.html",
            "/swagger-ui/**", "/webjars/**", "/druid/**","/**/*.js","/**/*.css",
            "/**/toLogin","/**/login");

    private static final AntPathMatcher ANTPATHMATCHER = new AntPathMatcher();

    /**
     * 获取白名单
     * @return List
     * */
    public static List<String> getPermitUrl(){
        return ENDPOINTS;
    }

    public static void addPermitUrl(String... urls){
        if (urls != null && urls.length != 0) {
            List<String> list = Arrays.asList(urls);
            ENDPOINTS.addAll(list);
        }
    }

    /**
     * 路径匹配器
     * @param url        url
     * @return boolean 匹配成功返回true
     */
    public static boolean matchPath(String url){
        for (String endpoint : ENDPOINTS) {
            if (ANTPATHMATCHER.match(endpoint,url)) {return true;}
        }
        return false;
    }
}
