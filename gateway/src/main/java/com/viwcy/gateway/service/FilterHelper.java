package com.viwcy.gateway.service;

import com.alibaba.fastjson.JSON;
import com.viwcy.gateway.common.ResultEntity;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class FilterHelper {

    /**
     * 重新设置Filter响应
     */
    public Mono<Void> response(ServerHttpResponse response, int code, String message) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(code, message, null);
        byte[] bits = JSON.toJSONString(resultEntity).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
