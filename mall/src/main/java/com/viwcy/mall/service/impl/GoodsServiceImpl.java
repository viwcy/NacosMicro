package com.viwcy.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.util.IDWorker;
import com.viwcy.basecommon.util.JwtUtil;
import com.viwcy.basemodel.dto.GoodsDTO;
import com.viwcy.basemodel.entity.GoodsEntity;
import com.viwcy.basemodel.mapper.GoodsMapper;
import com.viwcy.mall.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IDWorker idWorker;

    @Override
    public Boolean save(GoodsDTO dto) {
        GoodsEntity entity = new GoodsEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setPics(JSON.toJSONString(dto.getPics()));
        User user = jwtUtil.getUser();
        entity.setCreateUser(user.getId());
        entity.setUpdateUser(user.getId());
        entity.setGoodsNo(idWorker.getIdStr());
        return this.save(entity);
    }
}
