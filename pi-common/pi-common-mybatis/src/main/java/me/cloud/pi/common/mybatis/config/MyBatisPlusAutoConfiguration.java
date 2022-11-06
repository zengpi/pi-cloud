/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
