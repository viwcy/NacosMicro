package com.viwcy.custom.fun;

import com.viwcy.basecommon.entity.User;
import com.viwcy.custom.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class EditUserFunction implements InitializingBean {


    @Resource
    private UserService userService;

    public static final Map<Integer, EUFunction<User, String>> _function = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        _function.put(1, t -> userService.add(t));
        _function.put(2, t -> userService.updated(t));
    }

    @FunctionalInterface
    public interface EUFunction<T, R> extends Function<T, R> {

        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         * @return the function result
         */
        R apply(T t);
    }
}
