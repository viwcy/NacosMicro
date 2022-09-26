package com.viwcy.canalserver;

import com.viwcy.canalstater.event.EnableCanal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.viwcy.**"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableCanal(open = true)
public class CanalServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalServerApplication.class, args);
    }
}
