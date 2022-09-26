package com.viwcy.gateway.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -2247613031482303108L;

    private Long id;
    private String userName;
}
