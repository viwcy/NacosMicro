package com.viwcy.canalstater.event;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public class CanalRunner implements ApplicationRunner {

    private EventHandlerFactory eventHandlerFactory;

    public CanalRunner(EventHandlerFactory eventHandlerFactory) {
        this.eventHandlerFactory = eventHandlerFactory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //注册事件执行器
        eventHandlerFactory.register();
    }
}
