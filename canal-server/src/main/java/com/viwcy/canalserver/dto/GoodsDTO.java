package com.viwcy.canalserver.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class GoodsDTO {

    private Long id;
    private String goodsNo;
    private String title;
    private String description;
    private BigDecimal price;
    private String cover;
    private String pics;
    private String isShelve;
    private Long createUser;
    private Date createTime;
    private Long updateUser;
    private Date updateTime;
}
