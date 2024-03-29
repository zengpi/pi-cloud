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

package me.pi.cloud.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.pi.cloud.common.mybatis.handler.PiMetaObjectHandler;
import me.pi.cloud.common.mybatis.handler.SecurityMetaObjectHandler;
import me.pi.cloud.common.security.config.ResourceServerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ZnPi
 * @date 2022-08-30
 */
@Configuration
@Import(MyBatisPlusConfig.class)
public class MyBatisPlusAutoConfiguration {
    @Bean
    @ConditionalOnMissingClass("me.pi.cloud.common.security.config.ResourceServerConfiguration")
    public MetaObjectHandler piMetaObjectHandler(){
        return new PiMetaObjectHandler();
    }

    @Bean
    @ConditionalOnClass(ResourceServerConfiguration.class)
    public MetaObjectHandler securityMetaObjectHandler(){
        return new SecurityMetaObjectHandler();
    }
}
