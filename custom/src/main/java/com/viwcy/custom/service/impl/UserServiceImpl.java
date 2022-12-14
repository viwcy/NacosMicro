package com.viwcy.custom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viwcy.basecommon.config.JwtProperties;
import com.viwcy.basecommon.entity.User;
import com.viwcy.basecommon.exception.BusinessException;
import com.viwcy.basecommon.util.IDWorker;
import com.viwcy.basecommon.util.JwtUtil;
import com.viwcy.basemodel.convert.UserConvert;
import com.viwcy.basemodel.dto.SimpleUserDTO;
import com.viwcy.basemodel.dto.UserLoginDTO;
import com.viwcy.basemodel.dto.UserPageDTO;
import com.viwcy.basemodel.entity.PageEntity;
import com.viwcy.basemodel.mapper.UserMapper;
import com.viwcy.custom.fun.EditUserFunction;
import com.viwcy.custom.handle.RegisterChain;
import com.viwcy.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final IDWorker idWorker;
    private final UserMapper userMapper;
    private final HttpServletRequest request;
    private final JwtProperties jwtProperties;
    private final UserConvert userConvert;
    private final RegisterChain registerChain;

    @Override
    public synchronized Boolean register(User user) {
        log.info("??????????????????????????? = [{}]", JSONObject.toJSONString(user));

        //??????
        registerChain.check(user);
        //security????????????????????????????????????????????????????????????????????????????????????
        user.setPassword(encoder.encode(user.getPassword()));
        return this.save(user);
    }

    @Override
    public JSONObject login(UserLoginDTO dto) {
        log.info("????????????????????? = [{}]", JSON.toJSONString(dto));
        User user = userMapper.selectOne(new QueryWrapper<User>().and(wrapper -> wrapper.eq("phone", dto.getAccount()).or().eq("email", dto.getAccount())));
        if (!Optional.ofNullable(user).isPresent()) {
            throw new BusinessException("?????????????????????????????????????????????????????????");
        }
        //security????????????
        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("???????????????????????????????????????");
        }
        JSONObject jwt = jwtUtil.createJwt(user, idWorker.getIdStr());
        //TODO  ??????????????????????????????
        log.info("login success");
        return jwt;
    }

    @Override
    public JSONObject refresh() {
        String jwt = request.getHeader(jwtProperties.getHeader());
        return jwtUtil.refreshJwt(jwt, idWorker.getIdStr());
    }

    @Override
    public PageEntity<List<SimpleUserDTO>> queryPage(UserPageDTO dto) {
        User user = jwtUtil.getUser();
        log.info("request head for user = " + JSON.toJSONString(user));
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        if (StringUtils.isNotBlank(dto.getUserName())) {
            wrapper.like(User::getUserName, dto.getUserName());
        }
        Page<User> page = new Page<>(dto.getPage(), dto.getSize());
        Page<User> result = userMapper.selectPage(page, wrapper);
        List<SimpleUserDTO> collect = result.getRecords().stream().map(userConvert::toSimpleUserDTO).collect(Collectors.toList());
        return PageEntity.of(collect, result.getTotal());
    }

    @Override
    public String add(User user) {
        log.info("user = " + user);
        return "ADD";
    }

    @Override
    public String updated(User user) {
        log.info("user = " + user);
        return "UPDATE";
    }

    @Override
    public String edit(Integer type) {
        User user = jwtUtil.getUser();
        EditUserFunction.EUFunction<User, String> function = EditUserFunction._function.get(type);
        return function.apply(user);
    }
}
