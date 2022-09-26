package com.viwcy.basemodel.dto;

import com.viwcy.basemodel.param.PageRequest;
import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class UserPageDTO extends PageRequest {

    private String userName;
}
