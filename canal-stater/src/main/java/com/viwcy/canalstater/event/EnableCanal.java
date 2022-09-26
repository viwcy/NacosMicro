package com.viwcy.canalstater.event;

import com.viwcy.canalstater.config.CanalConfigurationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import({CanalAutoConfiguration.class, CanalRunner.class, SchedulingConfiguration.class})
@Import({CanalConfigurationSelector.class})
public @interface EnableCanal {

    boolean open() default true;
}
