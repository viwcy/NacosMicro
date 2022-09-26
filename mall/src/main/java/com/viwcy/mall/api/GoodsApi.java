package com.viwcy.mall.api;

import com.viwcy.basecommon.common.BaseController;
import com.viwcy.basecommon.common.ResultEntity;
import com.viwcy.basemodel.dto.GoodsDTO;
import com.viwcy.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RestController
@RequestMapping("/goods")
public class GoodsApi extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    public ResultEntity save(@RequestBody @Validated GoodsDTO dto) {
        Boolean save = this.goodsService.save(dto);
        return save ? ResultEntity.success("save success") : ResultEntity.fail("save fail");
    }
}
