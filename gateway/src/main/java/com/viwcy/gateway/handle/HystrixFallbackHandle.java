package com.viwcy.gateway.handle;

import com.viwcy.gateway.common.BaseController;
import com.viwcy.gateway.common.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

/**
 * TODO //、、Hystrix服务熔断
 */
@RestController
@Slf4j
@RequestMapping("/fallback")
public class HystrixFallbackHandle extends BaseController {

    @PostMapping("/custom")
    public ResultEntity customFallback(ServerWebExchange exchange) {
        Exception exception = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
        log.error("【custom】Hystrix服务熔断，request url = {}，e = {}", delegate.getRequest().getURI(), exception);
        return success("系统繁忙，请稍后重试");
    }

    @PostMapping("/mall")
    public ResultEntity mallFallback(ServerWebExchange exchange) {
        Exception exception = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
        log.error("【goods】Hystrix服务熔断，request url = {}，e = {}", delegate.getRequest().getURI(), exception);
        return success("系统繁忙，请稍后重试");
    }
}
