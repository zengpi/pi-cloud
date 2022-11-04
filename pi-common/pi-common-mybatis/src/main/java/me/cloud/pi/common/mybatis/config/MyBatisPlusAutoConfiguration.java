package me.cloud.pi.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.cloud.pi.common.mybatis.handler.PiMetaObjectHandler;
import me.cloud.pi.common.mybatis.handler.SecurityMetaObjectHandler;
import me.cloud.pi.common.security.config.ResourceServerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Configuration
public class MyBatisPlusAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ResourceServerConfiguration.class)
    public MetaObjectHandler piMetaObjectHandler(){
        return new PiMetaObjectHandler();
    }

    @Bean
    @ConditionalOnBean(ResourceServerConfiguration.class)
    public MetaObjectHandler securityMetaObjectHandler(){
        return new SecurityMetaObjectHandler();
    }
}
