package com.viwcy.custom.handle;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.basemodel.mapper.UserMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Order(0)
@Component
public class NameCheck extends AbstractRegisterHandle {

    @Resource
    private UserMapper userMapper;

    @Override
    protected boolean doCheck(User user) {

        User db = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUserName, user.getUserName()));
        if (Optional.ofNullable(db).isPresent()) {
            throw new BusinessException("该用户名已被占用，请重新输入");
        }
        return true;
    }
}
