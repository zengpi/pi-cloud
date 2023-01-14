package me.pi.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ZnPi
 * @date 2022-10-20
 */
@SpringBootApplication
@EnableFeignClients("me.pi.cloud.admin.api.feign")
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class);
    }
}
