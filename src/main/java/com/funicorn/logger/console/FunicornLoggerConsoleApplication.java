package com.funicorn.logger.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Aimee
 * @since 2023/03/24
 */
@SpringBootApplication
public class FunicornLoggerConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunicornLoggerConsoleApplication.class, args);
    }
}
