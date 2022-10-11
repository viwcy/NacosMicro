package com.viwcy.gateway.filter;

import com.viwcy.gateway.common.PermissionException;
import com.viwcy.gateway.dto.UserDTO;
import com.viwcy.gateway.service.FilterHelper;
import com.viwcy.gateway.service.PermissionHelper;
import com.viwcy.gateway.util.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Order(1)
@Component
public class PermissionFilter extends AbstractFilter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private PermissionHelper permissionHelper;
    @Resource
    private FilterHelper filterHelper;

    @Override
    protected Mono<Void> doFilter(FilterContext context) {

        ServerWebExchange exchange = context.getExchange();
        String jwt = context.getJwt();
        //获取请求url
        String requestUrl = exchange.getRequest().getURI().getPath();
        try {
            UserDTO user = jwtUtil.getUser(jwt);
            permissionHelper.check(user.getId(), requestUrl);
        } catch (PermissionException e) {
            return filterHelper.response(exchange.getResponse(), e.getCode(), e.getMessage());
        }
        return null;
    }
}
