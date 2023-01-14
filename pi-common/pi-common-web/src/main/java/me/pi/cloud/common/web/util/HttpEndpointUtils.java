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

package me.pi.cloud.common.web.util;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;

import java.io.IOException;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
public class HttpEndpointUtils implements ApplicationContextAware {
    private static HttpMessageConverters messageConverters;

    /**
     * 写出响应数据
     * @param responseData /
     * @param outputMessage /
     * @throws IOException /
     * @see AbstractMessageConverterMethodProcessor AbstractMessageConverterMethodProcessor#writeWithMessageConverters
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> void writeWithMessageConverters(T responseData, ServletServerHttpResponse outputMessage) throws IOException {
        for (HttpMessageConverter converter : HttpEndpointUtils.messageConverters) {
            Class<?> valueType = responseData.getClass();
            if(converter.canWrite(valueType, MediaType.APPLICATION_JSON)){
                converter.write(responseData, MediaType.APPLICATION_JSON, outputMessage);
                break;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HttpEndpointUtils.messageConverters = applicationContext.getBean(HttpMessageConverters.class);
    }
}