package com.viwcy.custom.handle;

import com.viwcy.basecommon.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class RegisterChain implements InitializingBean {

    @Resource
    private List<AbstractRegisterHandle> handlers;

    private AbstractRegisterHandle handler;

    public void check(User user) {

        handler.check(user);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        for (int i = 0; i < handlers.size(); i++) {
            if (i == 0) {
                this.handler = handlers.get(i);
            } else {
                AbstractRegisterHandle current = handlers.get(i - 1);
                current.setNext(handlers.get(i));
            }
        }
    }
}
