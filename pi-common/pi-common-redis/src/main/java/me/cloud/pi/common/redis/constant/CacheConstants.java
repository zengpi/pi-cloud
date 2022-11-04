package me.cloud.pi.common.redis.constant;

/**
 * @author ZnPi
 * @date 2022-10-26
 */
public interface CacheConstants {
    /**
     * 验证码前缀
     */
    String CAPTCHA_PREFIX = "captcha:";

    /**
     * 验证码有效期。单位：秒
     */
    Long CAPTCHA_TIME_OUT = 60L;
}
