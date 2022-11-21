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

package me.cloud.pi.common.logging.aspect;

import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.SneakyThrows;
import me.cloud.pi.admin.api.dto.LogDTO;
import me.cloud.pi.admin.api.enums.LogType;
import me.cloud.pi.common.logging.annotation.Log;
import me.cloud.pi.common.logging.event.LogEvent;
import me.cloud.pi.common.logging.util.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

/**
 * @author ZnPi
 * @date 2022-11-18
 */
@Aspect
public class LogAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Around("@annotation(log)")
    @SneakyThrows
    public Object log(ProceedingJoinPoint pjp, Log log) {
        long start = System.currentTimeMillis();

        Object obj;
        LogDTO logDTO = LogUtils.getDefaultLogDTO();
        logDTO.setTitle(log.value());

        fillLog(pjp, logDTO);

        try{
            obj = pjp.proceed();
        }catch (Throwable e){
            logDTO.setType(LogType.EXCEPTION.ordinal());
            logDTO.setExceptionDesc(e.getMessage());
            throw e;
        }finally {
            long end = System.currentTimeMillis();
            logDTO.setRequestTime(end - start);

            publisher.publishEvent(new LogEvent(logDTO, LogUtils.getToken()));
        }

        return obj;
    }

    private void fillLog(ProceedingJoinPoint pjp, LogDTO logDTO) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String className = pjp.getTarget().getClass().getName();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        logDTO.setMethodName(className + "#" + signature.getName());

        HashMap<String, Object> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            params.put(parameters[i].getName(), args[i]);
        }
        logDTO.setRequestParam(JSON.toJSONString(params));
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
