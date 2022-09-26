package com.viwcy.gateway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.viwcy.gateway.common.PermissionException;
import com.viwcy.gateway.constant.JwtEnum;
import com.viwcy.gateway.entity.MenuEntity;
import com.viwcy.gateway.entity.RoleMenuRelationEntity;
import com.viwcy.gateway.entity.UserRoleRelationEntity;
import com.viwcy.gateway.mapper.MenuMapper;
import com.viwcy.gateway.mapper.RoleMenuRelationMapper;
import com.viwcy.gateway.mapper.UserRoleRelationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PermissionHelper {

    private final UserRoleRelationMapper userRoleRelationMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuRelationMapper roleMenuRelationMapper;

    public final void check(Long userId, String url) {

        LambdaQueryWrapper<MenuEntity> mWrapper = Wrappers.lambdaQuery(MenuEntity.class);
        mWrapper.eq(MenuEntity::getUrl, url);
        MenuEntity menu = menuMapper.selectOne(mWrapper);
        if (Objects.isNull(menu)) {
            return;
        }

        LambdaQueryWrapper<UserRoleRelationEntity> ur = Wrappers.lambdaQuery(UserRoleRelationEntity.class);
        ur.eq(UserRoleRelationEntity::getUserId, userId);
        List<UserRoleRelationEntity> urList = userRoleRelationMapper.selectList(ur);
        if (CollectionUtils.isEmpty(urList)) {
            throw new PermissionException(JwtEnum.USER_ROLE_RELATION_ERROR.getCode(), JwtEnum.USER_ROLE_RELATION_ERROR.getMessage());
        }

        int count = 0;
        for (UserRoleRelationEntity urRelation : urList) {

            LambdaQueryWrapper<RoleMenuRelationEntity> rmr = Wrappers.lambdaQuery(RoleMenuRelationEntity.class);
            rmr.eq(RoleMenuRelationEntity::getRoleId, urRelation.getRoleId());
            rmr.eq(RoleMenuRelationEntity::getMenuId, menu.getId());
            RoleMenuRelationEntity rmrEntity = roleMenuRelationMapper.selectOne(rmr);
            if (!Objects.isNull(rmrEntity)) {
                count++;
                continue;
            }
        }
        if (count == 0) {
            throw new PermissionException(JwtEnum.UNAUTHORIZED_ERROR.getCode(), JwtEnum.UNAUTHORIZED_ERROR.getMessage());
        }
    }

}
