package com.viwcy.canalstater.config;

import com.viwcy.canalstater.event.CanalRunner;
import com.viwcy.canalstater.event.EnableCanal;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public class CanalConfigurationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        List<String> imports = new ArrayList<>();
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableCanal.class.getName());
        Set<Map.Entry<String, Object>> entries = attributes.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (entry.getKey().equals("open") && (boolean) entry.getValue()) {
                imports.add(CanalAutoConfiguration.class.getName());
                imports.add(CanalRunner.class.getName());
                imports.add(SchedulingConfiguration.class.getName());
            }
        }
        return imports.toArray(new String[imports.size()]);
    }
}
