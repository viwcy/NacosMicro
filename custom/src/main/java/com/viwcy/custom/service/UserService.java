package com.viwcy.custom.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserLoginDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface UserService extends IService<User> {

    Boolean register(User userEntity);

    JSONObject login(UserLoginDTO dto);

    JSONObject refresh();

    PageEntity<List<SimpleUserDTO>> queryPage(UserPageDTO dto);

    String add(User user);

    String updated(User user);

    String edit(Integer type);
}
