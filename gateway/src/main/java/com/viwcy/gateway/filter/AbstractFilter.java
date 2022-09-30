package com.viwcy.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public abstract class AbstractFilter {

    //下个节点
    private AbstractFilter next;

    public Mono<Void> filter(String jwt, ServerWebExchange exchange, GatewayFilterChain chain) {

        Mono<Void> mono = this.doFilter(jwt, exchange, chain);
        if (!Objects.isNull(mono)) {
            return mono;
        }
        //判断下个节点执行
        if (!Objects.isNull(next)) {
            return this.next.filter(jwt, exchange, chain);
        }
        return null;
    }

    //业务filter
    protected abstract Mono<Void> doFilter(String jwt, ServerWebExchange exchange, GatewayFilterChain chain);
}
