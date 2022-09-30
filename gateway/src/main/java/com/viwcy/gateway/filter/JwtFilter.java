package com.viwcy.gateway.filter;

import com.viwcy.gateway.constant.JwtEnum;
import com.viwcy.gateway.service.FilterHelper;
import com.viwcy.gateway.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Order(0)
@Component
public class JwtFilter extends AbstractFilter {

    @Resource
    private FilterHelper filterHelper;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected Mono<Void> doFilter(String jwt, ServerWebExchange exchange, GatewayFilterChain chain) {

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
        return null;
    }
}
