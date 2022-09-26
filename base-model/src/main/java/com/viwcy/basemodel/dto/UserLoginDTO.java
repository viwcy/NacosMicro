package com.viwcy.basemodel.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class UserLoginDTO {

    @NotBlank(message = "请输入账号")
    private String account;
    @NotBlank(message = "请输入密码")
    private String password;
}
