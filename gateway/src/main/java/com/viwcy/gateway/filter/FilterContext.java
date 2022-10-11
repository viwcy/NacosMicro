package com.viwcy.gateway.filter;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.server.ServerWebExchange;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@Builder
public class FilterContext implements Serializable {

    private String jwt;
    private ServerWebExchange exchange;
}
