package com.viwcy.canalserver.constant;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public class Constants {

    //服务名
    public static final String MALL_SERVER_NAME = "mall:";
    public static final String CUSTOM_SERVER_NAME = "custom:";

    //业务
    public static final String MALL_OPERATION_GOODS = "goods:";
    public static final String CUSTOM_OPERATION_USER = "user:";


    //canal监听的库和表，向工厂注册handle
    public static final String CUSTOM_DATABASE = "nacos_micro_custom";
    public static final String MALL_DATABASE = "nacos_micro_mall";
    public static final String CUSTOM_TABLE_USER = "user";
    public static final String MALL_TABLE_GOODS = "goods";
}
