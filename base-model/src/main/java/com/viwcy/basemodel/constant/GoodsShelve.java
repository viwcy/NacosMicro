package com.viwcy.basemodel.constant;


/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public enum GoodsShelve {

    SHELVE(1, "上架"),

    NO_SHELVE(2, "下架");

    private final Integer value;
    private final String desc;


    GoodsShelve(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
