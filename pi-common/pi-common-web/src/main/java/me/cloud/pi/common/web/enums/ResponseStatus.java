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

package me.cloud.pi.common.web.enums;

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
    CLIENT_ERROR("A0001", "客户端错误"),
    USER_REGISTRATION_ERROR("A0100", "用户注册错误"),
    USER_NAME_VERIFICATION_FAILED("A0101", "用户名校验失败"),
    USER_NAME_HAS_EXISTS("A0102", "用户名已存在"),
    USER_NAME_CONTAINS_SPECIAL_CHARACTERS("A0103", "用户名包含特殊字符"),
    PASSWORD_VERIFICATION_FAILED("A0104", "密码校验失败"),
    SHORT_PASSWORD_LENGTH("A0105", "密码长度不够"),
    WEAK_PASSWORD_STRENGTH("A0106", "密码强度不够"),
    VERIFICATION_CODE_INCORRECT("A0107", "校验码输入错误"),
    SMS_VERIFICATION_CODE_INCORRECT("A0108", "短信校验码输入错误"),
    EMAIL_VERIFICATION_CODE_INCORRECT("A0109", "邮件校验码输入错误"),
    EMAIL_FORMAT_VERIFICATION_FAIL("A0110", "邮箱格式校验失败"),

    USER_LOGIN_ABNORMAL("A0200", "用户登录异常"),
    ACCOUNT_NOT_EXIST("A0201", "用户账户不存在"),
    ACCOUNT_FROZEN("A0202", "用户账户被冻结"),
    ACCOUNT_INVALID("A0203", "用户账户已作废"),
    ACCOUNT_EXPIRED("A0204", "用户账户已过期"),
    ACCOUNT_DISABLE("A0205", "用户账户已禁用"),
    PASSWORD_INCORRECT("A0206", "用户密码错误"),
    OLD_PASSWORD_INCORRECT("A0207", "用户旧密码错误"),
    PASSWORD_EXPIRED("A0208", "用户密码已过期"),
    USERNAME_OR_PASSWORD_INCORRECT("A0209", "用户名或密码不正确"),
    INCORRECT_PASSWORD_UPPER_LIMIT("A0210", "用户输入密码错误次数超限"),
    INVALID_SCOPE("A0211", "无效的 scope"),
    CLIENT_NOT_EXIST("A0212", "客户端不存在"),
    CLIENT_PASSWORD_EMPTY("A0213", "客户端密码不能为空"),
    CLIENT_PASSWORD_INCORRECT("A0214", "客户端密码错误"),
    UNAUTHORIZED_CLIENT("A0215", "未授权的客户端"),
    GRANT_TYPE_EMPTY("A0216", "授权类型不能为空"),
    UNSUPPORTED_GRANT_TYPE("A0217", "不支持的授权类型"),
    INVALID_GRANT("A0218", "无效的授权"),
    IDENTITY_VERIFICATION_FAILED("A0219", "用户身份校验失败"),
    CAPTCHA_EMPTY("A0220", "用户验证码不能为空"),
    CAPTCHA_INCORRECT("A0221", "用户验证码错误"),
    INCORRECT_CAPTCHA_LIMIT("A0222", "用户验证码尝试次数超限"),
    NOT_THIRD_PARTY_LOGIN_AUTHORIZATION("A0223", "用户未获得第三方登录授权"),
    LOGIN_EXPIRED("A0224", "用户登录已过期"),

    ACCESS_PERMISSION_ABNORMAL("A0301", "访问权限异常"),
    UNAUTHORIZED("A0302", "访问未授权"),
    AUTHORIZATION_DENIED("A0303", "用户授权申请被拒绝"),
    AUTHORIZATION_EXPIRED("A0304", "授权已过期"),
    WITHOUT_PERMISSION_USE_API("A0305", "无权限使用 API"),
    ACCESS_BLOCKED("A0306", "用户访问被拦截"),
    BLACKLIST_USERS("A0307", "黑名单用户"),
    LOGIN_ACCOUNT_FROZEN("A0308", "账户被冻结"),
    RESTRICTED_GATEWAY_ACCESS("A0309", "网关访问受限"),

    REQUEST_PARAM_ERROR("A0400", "用户请求参数错误"),
    INVALID_USER_INPUT("A0401", "无效的用户输入"),
    REQUIRED_PARAM_EMPTY("A0402", "请求必填参数为空"),
    PARSE_JSON_FAIL("A0403", "请求 JSON 解析失败"),
    USER_OPERATION_EXCEPTION("A0404", "用户操作异常"),

    REQUEST_SERVICE_ABNORMAL("A0500", "用户请求服务异常"),
    REQUESTS_NUMBER_EXCEEDED_LIMIT("A0501", "请求次数超出限制"),
    REPEAT_REQUEST("A0502", "用户重复请求"),

    RESOURCE_EXCEPTION("A0600", "用户资源异常"),
    UPLOAD_FILE_FAIL("A0601", "用户上传文件异常"),
    UPLOAD_FILE_TYPE_NOT_MATCH("A0602", "用户上传文件类型不匹配"),
    UPLOAD_FILE_TOO_LARGE("A0603", "用户上传文件太大"),
    UPLOAD_PIC_TOO_LARGE("A0604", "用户上传图片太大"),
    UPLOAD_VIDEO_TOO_LARGE("A0605", "用户上传视频太大"),
    UPLOAD_ZIP_TOO_LARGE("A0606", "用户上传压缩文件太大"),

    /*
    错误来源于服务端
     */
    SYS_ERROR("B0001", "服务器内部错误"),
    SYS_TIMEOUT_ERROR("B0100", "系统执行超时"),
    SYS_LIMIT("B0101", "系统限流"),
    SYS_DEGRADATION("B0102", "系统功能降级"),

    RESOURCE_ABNORMAL("B0200", "系统资源异常"),
    RESOURCE_DEPLETION("B0201", "系统资源耗尽"),

    ILLEGAL_ARGUMENT_EXCEPTION("B0300", "非法参数异常"),

    ACCESS_TOKEN_GEN_FAIL("B0400", "访问令牌生成失败"),
    REFRESH_TOKEN_GEN_FAIL("B0400", "访问令牌生成失败"),

    /*
    错误来源于第三方服务
     */
    THIRD_PARTY_ERROR("C0001", "调用第三方服务出错"),
    API_NOT_EXIST("C0100", "接口不存在"),
    MESSAGE_SERVICE_ERROR("C0101", "消息服务出错"),
    MESSAGE_DELIVERY_ERROR("C0102", "消息投递出错"),
    MESSAGE_CONSUMPTION_ERROR("C0103", "消息消费出错"),
    MESSAGE_SUBSCRIPTION_ERROR("C0104", "消息订阅出错"),
    CACHE_SERVICE_ERROR("C0105", "缓存服务出错"),
    KEY_LENGTH_LIMIT("C0106", "key 长度超过限制"),
    VALUE_LENGTH_LIMIT("C0107", "value 长度超过限制"),
    STORAGE_CAPACITY_FULL("C0108", "存储容量已满"),

    THIRD_PARTY_TIMEOUT("C0200", "第三方系统执行超时"),
    MESSAGE_DELIVERY_TIMEOUT("C0201", "消息投递超时"),
    CACHE_SERVICE_TIMEOUT("C0202", "缓存服务超时");

    private String code;
    private String msg;

    ResponseStatus(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
