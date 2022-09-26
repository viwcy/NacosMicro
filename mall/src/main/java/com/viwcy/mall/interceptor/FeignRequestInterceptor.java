package com.viwcy.mall.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 * <p>
 * 解决feign调用请求头丢失问题
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //从RequestContextHolder中获取HttpServletRequest
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取RequestContextHolder中的信息
        Map<String, String> headers = getHeaders(request);
        //放入feign的RequestTemplate中
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            template.header(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取原请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                // 跳过 content-length
                if (key.equalsIgnoreCase("content-length")) {
                    continue;
                }
                map.put(key, value);
            }
        }
        return map;
    }
}
