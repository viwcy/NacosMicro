package com.viwcy.basemodel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.viwcy.basecommon.entity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role_relation")
@Builder
public class UserRoleRelationEntity extends AbstractBaseEntity<UserRoleRelationEntity> implements Serializable {
    private static final long serialVersionUID = -4411969238074687894L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long roleId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
