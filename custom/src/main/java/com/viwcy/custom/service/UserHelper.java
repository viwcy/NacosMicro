package com.viwcy.custom.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.basemodel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class UserHelper {

    @Autowired
    private UserMapper userMapper;

    public final void registerVerify(String userName, String phone, String email) {

        checkNickName(userName);
        checkPhone(phone);
        checkEmail(email);
    }

    public final void checkNickName(String userName) {

        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUserName, userName));
        if (Optional.ofNullable(user).isPresent()) {
            throw new BusinessException("该用户名已被占用，请重新输入");
        }
    }

    public final void checkPhone(String phone) {

        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getPhone, phone));
        if (Optional.ofNullable(user).isPresent()) {
            throw new BusinessException("该手机号已被注册，请重新输入");
        }
    }

    public final void checkEmail(String email) {

        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (Optional.ofNullable(user).isPresent()) {
            throw new BusinessException("该邮箱已被注册，请重新输入");
        }
    }
}
