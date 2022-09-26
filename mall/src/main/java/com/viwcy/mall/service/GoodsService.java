package com.viwcy.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.viwcy.basemodel.dto.GoodsDTO;
import com.viwcy.basemodel.entity.GoodsEntity;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface GoodsService extends IService<GoodsEntity> {

    //保存
    Boolean save(GoodsDTO dto);
}
