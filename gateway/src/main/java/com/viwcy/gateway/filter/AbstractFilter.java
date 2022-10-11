package com.viwcy.gateway.filter;

import lombok.Data;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public abstract class AbstractFilter {

    //下个节点
    private AbstractFilter next;

    public Mono<Void> filter(FilterContext context) {

        Mono<Void> mono = this.doFilter(context);
        if (!Objects.isNull(mono)) {
            return mono;
        }
        //判断下个节点执行
        if (!Objects.isNull(next)) {
            return this.next.filter(context);
        }
        return null;
    }

    //业务filter
    protected abstract Mono<Void> doFilter(FilterContext context);
}
