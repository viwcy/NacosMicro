package com.viwcy.basemodel.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -6716691971860680022L;

    /**
     * {@link JsonProperty}
     * access = JsonProperty.Access.WRITE_ONLY  可接收参数,而不会序列化字符串（屏蔽该字段的返回）
     * access = JsonProperty.Access.READ_ONLY   可序列化为字符串,而不会接收
     */
    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias(value = {"current"})
    private int page = 1;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias(value = {"pageSize"})
    private int size = 10;
}
