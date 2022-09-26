package com.viwcy.gateway.filter;

import com.viwcy.gateway.common.PermissionException;
import com.viwcy.gateway.constant.JwtEnum;
import com.viwcy.gateway.dto.UserDTO;
import com.viwcy.gateway.entity.IgnoreValidationUrl;
import com.viwcy.gateway.service.FilterHelper;
import com.viwcy.gateway.service.IgnoreValidationUrlService;
import com.viwcy.gateway.service.PermissionHelper;
import com.viwcy.gateway.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    private final JwtUtil jwtUtil;
    private final IgnoreValidationUrlService ignoreService;
    private final FilterHelper filterHelper;
    private final PermissionHelper permissionHelper;

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
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isBlank(jwt)) {
            return filterHelper.response(response, JwtEnum.JWT_MISS.getCode(), JwtEnum.JWT_MISS.getMessage());
        }
        //解析jwt
        try {
            jwtUtil.parsingJwt(jwt);
        } catch (ExpiredJwtException e) {
            return filterHelper.response(response, JwtEnum.JWT_EXPIRED.getCode(), JwtEnum.JWT_EXPIRED.getMessage());
        } catch (SignatureException e) {
            return filterHelper.response(response, JwtEnum.JWT_INVALID.getCode(), JwtEnum.JWT_INVALID.getMessage());
        } catch (Exception e) {
            return filterHelper.response(response, JwtEnum.JWT_ERROR.getCode(), JwtEnum.JWT_ERROR.getMessage());
        }

        //权限校验
        try {
            UserDTO user = jwtUtil.getUser(jwt);
            permissionHelper.check(user.getId(), requestUrl);
        } catch (PermissionException e) {
            return filterHelper.response(exchange.getResponse(), e.getCode(), e.getMessage());
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
