package me.cloud.pi.common.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 服务间调用自动鉴权
 *
 * @author ZnPi
 * @date 2022-08-15
 */
public class CustomRequestInterceptor implements RequestInterceptor {
    private static final String HEADER = "Authorization";
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            String authorization = attributes.getRequest().getHeader(HEADER);
            if(StringUtils.hasText(authorization)){
                template.header(HEADER, authorization);
            }
        }
    }
}