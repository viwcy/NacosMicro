package com.viwcy.mall.feign.fallback;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.mall.feign.CustomHandle;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class CustomHandleFallback extends BaseController implements CustomHandle {
    @Override
    public ResultEntity<PageEntity<List<SimpleUserDTO>>> page(UserPageDTO dto) {
        return success("系统繁忙，请稍后再试");
    }
}
