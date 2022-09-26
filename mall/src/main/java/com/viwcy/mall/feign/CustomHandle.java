package com.viwcy.mall.feign;

import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.mall.feign.config.CustomFeignConfiguration;
import com.viwcy.mall.feign.factory.CustomFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@FeignClient(value = "custom", configuration = CustomFeignConfiguration.class, fallbackFactory = CustomFallbackFactory.class)
public interface CustomHandle {

    @PostMapping("user/page")
    ResultEntity<PageEntity<List<SimpleUserDTO>>> page(@RequestBody UserPageDTO dto);
}
