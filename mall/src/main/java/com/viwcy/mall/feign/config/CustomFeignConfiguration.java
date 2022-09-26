package com.viwcy.mall.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO //
 */
@Configuration
public class CustomFeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }
}
