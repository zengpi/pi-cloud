package me.cloud.pi.gateway.enums;

/**
 * 响应状态码
 *
 * @author ZnPi
 * @date 2022-10-21
 */
public enum ResponseStatus {
    /*
    一切 OK
     */
    OK("00000", "一切 ok"),

    /*
    错误来源与客户端
     */
    CAPTCHA_EMPTY("A0220", "用户验证码不能为空"),
    CAPTCHA_INCORRECT("A0221", "用户验证码错误"),

    /*
    错误来源于服务端
     */
    SYS_ERROR("B0001", "服务器内部错误");

    private String code;
    private String msg;

    ResponseStatus(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
