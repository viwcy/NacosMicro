package com.viwcy.gateway.constant;

/**
 * TODO //
 *
 * <p> Title: JwtEnum </p>
 * <p> Description: JwtEnum </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
public enum JwtEnum {

    /**
     * 统一处理filter抛出的JWT相关的异常 返回给前端标准格式的json和装填码
     */
    JWT_MISS(401, "Missing or invalid Authorization header"),
    JWT_EXPIRED(401, "JWT has expired, please login in again"),
    JWT_INVALID(401, "JWT is invalid, incorrectly formatted or signed"),
    JWT_ERROR(401, "System error unknown"),
    USER_ROLE_RELATION_ERROR(401, "The user role binding relationship does not exist"),
    UNAUTHORIZED_ERROR(401, "Call without permission");

    private int code;
    private String message;

    JwtEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
