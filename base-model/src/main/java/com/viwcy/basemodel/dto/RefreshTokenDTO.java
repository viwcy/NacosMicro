package com.viwcy.basemodel.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class RefreshTokenDTO {

    @NotBlank(message = "refresh_token不能为空")
    private String jwt;
}
