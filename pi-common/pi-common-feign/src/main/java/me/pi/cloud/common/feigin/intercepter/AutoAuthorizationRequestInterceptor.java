package me.pi.cloud.common.feigin.intercepter;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import me.pi.cloud.common.feigin.util.AccessTokenHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 服务间调用自动鉴权
 *
 * @author ZnPi
 * @date 2022-08-15
 */
public class AutoAuthorizationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String authorization = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(authorization)) {
                template.header(HttpHeaders.AUTHORIZATION, authorization);
            }
        } else {
            try {
                if (StrUtil.isNotBlank(AccessTokenHolder.getToken())) {
                    template.header(HttpHeaders.AUTHORIZATION, AccessTokenHolder.getToken());
                }
            } finally {
                AccessTokenHolder.remove();
            }
        }
    }
}