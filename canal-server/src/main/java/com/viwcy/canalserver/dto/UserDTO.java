package com.viwcy.canalserver.dto;

import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class UserDTO {

    private Long id;
    private String userName;
    private String headPhoto;
    private String phone;
    private String email;
    private String password;
}
