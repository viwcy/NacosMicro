package com.viwcy.gateway.filter;

import com.viwcy.gateway.entity.IgnoreValidationUrl;
import com.viwcy.gateway.service.IgnoreValidationUrlService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO //
 *
 * <p> Title: JwtFilter </p >
 * <p> Description: JwtFilter </p >
 * <p> History: 2021/4/13 11:04 </p >
 * <pre>
 *      Copyright (c) 2020 FQ (fuqiangvn@163.com) , ltd.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PreFilter implements GlobalFilter, Ordered {

    private final IgnoreValidationUrlService ignoreService;
    private final FilterChain filterChain;

    @Value("${jwt.config.header}")
    private String header;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求url
        String requestUrl = exchange.getRequest().getURI().getPath();
        //数据库获取过滤url
        Set<String> _ignore = ignoreService.list().stream().map(IgnoreValidationUrl::getIgnoreUrl).collect(Collectors.toSet());
        for (String url : _ignore) {
            if (requestUrl.startsWith(url)) {
                return chain.filter(exchange);
            }
        }

        String jwt = exchange.getRequest().getHeaders().getFirst(header);

        //链化校验（登录，权限等）
        Mono<Void> execute = filterChain.filter(jwt, exchange, chain);
        if (!Objects.isNull(execute)) {
            return execute;
        }

        //放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
