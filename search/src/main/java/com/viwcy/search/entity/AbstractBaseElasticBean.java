package com.viwcy.search.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public abstract class AbstractBaseElasticBean<T> implements Serializable {

    private static final long serialVersionUID = -2904259238579192130L;

    //ES自身主键ID
    public abstract String _id();

    public T t() {

        return (T) this;
    }

    private float score = 0.0f;

    /**
     * 限制入参格式，es内部使用long存储，默认查询出来就是当前限制格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
