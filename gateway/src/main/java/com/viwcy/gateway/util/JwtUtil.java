package com.viwcy.gateway.util;

import com.alibaba.fastjson.JSON;
import com.viwcy.gateway.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * TODO //jwt工具模板
 *
 * <p> Title: JwtTemplate </p>
 * <p> Description: JwtTemplate </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Component
@Slf4j
public class JwtUtil implements EnvironmentAware {

    private String secret;
    private String prefix;

    /**
     * @param jwt
     * @return {@link Claims}
     * @Description TODO    解析jwt
     * @date 2020/9/3 17:40
     * @author Fuqiang
     */
    public final Claims parsingJwt(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt.replace(prefix + " ", "")).getBody();
    }

    public final UserDTO getUser(String jwt) {
        Object user = this.parsingJwt(jwt).get("user");
        return JSON.parseObject(JSON.toJSONString(user), UserDTO.class);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.secret = environment.getProperty("jwt.config.secret");
        this.prefix = environment.getProperty("jwt.config.prefix");
    }
}
