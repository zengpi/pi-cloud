package me.pi.cloud.admin;

import me.pi.cloud.common.file.annotation.EnableMinio;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author ZnPi
 * @date 2023-01-13 21:13
 */
@SpringBootApplication
@MapperScan("me.pi.cloud.admin.mapper")
@EnableCaching
@EnableMinio
public class AdminApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class);
    }
}