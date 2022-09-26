package com.viwcy.search.handle;

import com.viwcy.search.constant.SearchConstant;
import com.viwcy.search.handle.base.AbstractBaseSearch;
import com.viwcy.search.param.base.BaseSearchReq;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Service(SearchConstant.USER_SEARCH_HANDLER)
public class UserSearchHandle extends AbstractBaseSearch {

    @Override
    protected SearchSourceBuilder buildQuery(BaseSearchReq req) {

        return null;
    }

    @Override
    protected float getBoost(String field) {
        float boost;
        switch (field) {
            case "userName":
                boost = 5.0f;
                break;
            case "phone":
                boost = 3.0f;
                break;
            case "email":
                boost = 2.0f;
                break;
            default:
                boost = 1.0f;
        }
        return boost;
    }
}
