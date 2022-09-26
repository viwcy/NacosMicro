package com.viwcy.mall.feign.factory;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.mall.feign.CustomHandle;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
@Slf4j
public class CustomFallbackFactory extends BaseController implements FallbackFactory<CustomHandle> {

    @Override
    public CustomHandle create(Throwable throwable) {

        return (UserPageDTO dto) -> {
            log.error("Feign调用下游服务[custom]发生熔断异常, e = " + throwable);
            return success("请求人数较多，请稍后重试");
        };
    }
}
