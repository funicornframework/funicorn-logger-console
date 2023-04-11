package com.funicorn.logger.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Aimee
 * @since 2023/3/6 11:45
 */
@Configuration
public class LoggerConsoleConfiguration {

    /**
     * 密码校验
     * @return BCryptPasswordEncoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("taskScheduler-");
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskScheduler;
    }
}
