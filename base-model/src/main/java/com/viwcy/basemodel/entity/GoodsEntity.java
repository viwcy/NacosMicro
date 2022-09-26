package com.viwcy.basemodel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.viwcy.basecommon.entity.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods")
public class GoodsEntity extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = -6105334111346709277L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String goodsNo;
    private String title;
    private String description;
    private BigDecimal price;
    private String cover;
    private String pics;
    private Integer isShelve;
    private Long createUser;
    private Long updateUser;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
