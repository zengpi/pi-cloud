package me.cloud.pi.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZnPi
 * @date 2022-08-08
 */
@SpringBootApplication
@MapperScan("me.cloud.pi.admin.mapper")
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class, args);
    }
}
