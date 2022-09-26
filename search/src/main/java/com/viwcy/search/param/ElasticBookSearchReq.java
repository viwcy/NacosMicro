package com.viwcy.search.param;

import com.google.common.collect.Lists;
import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.param.base.BaseSearchReq;
import lombok.Data;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public class ElasticBookSearchReq extends BaseSearchReq {

    @Override
    public String[] getIndices() {

        List<String> list = Lists.newArrayList(SearchConstant.BOOK_INDEX);
        return list.toArray(new String[]{});
    }
}
