package com.viwcy.search.param.base;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageReq implements Serializable {

    private static final long serialVersionUID = 50955116296294356L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias(value = {"current"})
    private int page = 1;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias(value = {"pageSize"})
    private int size = 10;
}
