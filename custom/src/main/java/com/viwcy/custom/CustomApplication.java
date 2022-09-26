package com.viwcy.custom;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.viwcy.**.mapper"})
@ComponentScan(basePackages = {"com.viwcy.**"})
@EnableFeignClients
public class CustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * druid控制台相关配置，启动项目访问：http://ip:port/druid/sql.html
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        //先配置管理后台的servLet，访问的入口为/druid/
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "");
        servletRegistrationBean.addInitParameter("loginUsername", "viwcy");
        servletRegistrationBean.addInitParameter("loginPassword", "viwcy");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
