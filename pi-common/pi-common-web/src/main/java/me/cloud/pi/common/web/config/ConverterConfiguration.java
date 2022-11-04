package me.cloud.pi.common.web.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Collections;

/**
 * @author ZnPi
 * @date 2022-08-14
 */
@Configuration(proxyBeanMethods = false)
public class ConverterConfiguration {
    /**
     * 转换HTTP请求和响应
     *
     * @return /
     */
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();
        config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converter.setFastJsonConfig(config);

        return new HttpMessageConverters(converter);
    }
}