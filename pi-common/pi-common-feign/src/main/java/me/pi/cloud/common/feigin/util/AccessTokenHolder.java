package me.pi.cloud.common.feigin.util;

/**
 * @author ZnPi
 * @date 2022-11-19
 */
public class AccessTokenHolder {
    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    public static void setToken(String token){
        TOKEN.set(token);
    }

    public static String getToken(){
        return TOKEN.get();
    }

    public static void remove(){
        TOKEN.remove();
    }
}
