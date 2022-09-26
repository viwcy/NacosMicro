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
@TableName("role")
@Builder
public class RoleEntity extends AbstractBaseEntity<RoleEntity> implements Serializable {
    private static final long serialVersionUID = -5597521138666799464L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
