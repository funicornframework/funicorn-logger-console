package com.funicorn.logger.console;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootTest
class FunicornLoggerConsoleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void passEncode(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println(passwordEncoder.encode("123456"));
        // $2a$10$q/YC8KhlHLSeJDp1cQPiSOxA/q/5NjdYTMWvNuqxGNBXF2WxPAW12
        System.out.println(passwordEncoder.matches("123456","$2a$10$q/YC8KhlHLSeJDp1cQPiSOxA/q/5NjdYTMWvNuqxGNBXF2WxPAW12"));
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
}
