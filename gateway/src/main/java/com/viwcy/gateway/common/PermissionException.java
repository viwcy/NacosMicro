package com.viwcy.gateway.common;

import org.springframework.http.HttpStatus;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
public class PermissionException extends BaseException {

    public PermissionException() {
    }

    public PermissionException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public PermissionException(Integer code, String message) {
        super(code, message);
    }
}
