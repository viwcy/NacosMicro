package com.viwcy.search.handle.base;

import com.viwcy.search.param.base.BaseSearchReq;
import com.viwcy.search.vo.PageVO;

import java.util.List;


/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface BaseSearch {

    <T> PageVO<T> page(Class<T> clazz, BaseSearchReq req);

    <T> List<T> list(Class<T> clazz, BaseSearchReq req);
}