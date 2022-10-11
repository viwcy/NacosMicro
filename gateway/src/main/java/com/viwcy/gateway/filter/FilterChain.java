package com.viwcy.gateway.filter;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
@Data
public class FilterChain implements InitializingBean {

    @Resource
    private List<AbstractFilter> filters;

    //经过链化的节点，带next节点
    private AbstractFilter abstractFilter;

    //外部调用
    public Mono<Void> filter(FilterContext context) {

        return abstractFilter.filter(context);
    }

    //链化所有节点
    @Override
    public void afterPropertiesSet() throws Exception {

        for (int i = 0; i < filters.size(); i++) {

            if (i == 0) {
                this.abstractFilter = filters.get(i);
            } else {
                AbstractFilter current = filters.get(i - 1);
                current.setNext(filters.get(i));
            }
        }
    }

}
