package com.viwcy.basemodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class SimpleUserDTO implements Serializable {

    private Long id;
    private String name;
    private String headPortrait;//头像
    private String phone;
    private String email;
}
